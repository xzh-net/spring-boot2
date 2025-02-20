package net.xzh.gitlab.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectUser;
import org.gitlab4j.api.models.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project(项目) 相关操作
 * <p>
 * 例如对任务的增、删、改、查等操作
 */
public class ProjectApi {

	private static final Logger log = LoggerFactory.getLogger(ProjectApi.class);

	/**
	 * GitLab登录地址
	 */
	public static final String GITLAB_REMOTE_URL = "http://git.vjspnet.cn/";
	/**
	 * gitLab登录账号
	 */
	public static final String GITLAB_REMOTE_USERNAME = "13998417419";
	/**
	 * GitLab登录密码
	 */
	public static final String GITLAB_REMOTE_PWD = "13998417419";

	private GitLabApi gitLabApi;

	ProjectApi() {
		try {
			gitLabApi = GitLabApi.oauth2Login(GITLAB_REMOTE_URL, GITLAB_REMOTE_USERNAME, GITLAB_REMOTE_PWD);
		} catch (GitLabApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 查询所有仓库
	 */
	public void listProject() {
		try {
			List<Project> projects = gitLabApi.getProjectApi().getProjects(1, 10);
			for (Project project : projects) {
				log.info(project.getName());
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询归属自己仓库
	 */
	public void ownedProjects() {
		try {
			List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects(1, 10);
			for (Project project : projects) {
				log.info(project.getName());
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询星标仓库
	 */
	public void starredProjects() {
		try {
			List<Project> projects = gitLabApi.getProjectApi().getStarredProjects(1, 10);
			for (Project project : projects) {
				log.info(project.getName());
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询项目成员
	 */
	public void memberProjects(Long projectId) {
		try {
			List<Member> members = gitLabApi.getProjectApi().getMembers(projectId);
			for (Member member : members) {
				log.info("名称：{},  Username：{} 访问级别：{}", member.getName(), member.getUsername(),
						member.getAccessLevel());
			}
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询项目用户 项目用户包括：项目成员+群组用户，两部分组成
	 *
	 * @param projectId
	 */

	public void projectUsers(Long projectId) {
		try {
			List<ProjectUser> projectUsers = gitLabApi.getProjectApi().getProjectUsers(projectId);
			for (ProjectUser projectUser : projectUsers) {
				log.info("名称：{}, Username：{}", projectUser.getName(), projectUser.getUsername());
			}
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建仓库
	 */
	public void createProject() {
		Project project = new Project().withName("xzh-test-" + System.currentTimeMillis());
		project.withDescription("仓库描述123");
		project.setVisibility(Visibility.PUBLIC);// 公开
		try {
			Project createProject = gitLabApi.getProjectApi().createProject(project);
			System.out.println(createProject);
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改仓库
	 */
	public void updateProject(Long projectId) {
		Project project = new Project();
		project.setId(projectId);
		project.withDescription("仓库描述123_update");
		project.setVisibility(Visibility.PRIVATE);// 私有
		try {
			Project createProject = gitLabApi.getProjectApi().updateProject(project);
			System.out.println(createProject);
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除仓库
	 *
	 * @param projectId
	 */
	public void deleteProject(Long projectId) {
		try {
			gitLabApi.getProjectApi().deleteProject(projectId);
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 仓库归档
	 *
	 * @param projectId
	 */
	public void archiveProject(Long projectId) {
		try {
			gitLabApi.getProjectApi().archiveProject(projectId);
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解除归档
	 *
	 * @param projectId
	 */
	public void unArchiveProject(Long projectId) {
		try {
			gitLabApi.getProjectApi().unarchiveProject(projectId);
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照关键字查询仓库 查询仓库成员代码提交量
	 *
	 * @throws Exception
	 */
	public void rowsStatistics(Object projectIdOrPath) throws Exception {
		// 记录开始时间
		long startTime = System.currentTimeMillis();
		// 获取指定名称的项目列表
		Project project = gitLabApi.getProjectApi().getProject(projectIdOrPath);
		String namespace = project.getNamespace().getFullPath();
		String proName = project.getPath();
		String proUrl = project.getWebUrl();
		log.info("项目命名空间：{}, 项目名称：{}, 项目地址：{}", namespace, proName, proUrl);

		// 定义统计时间范围
		LocalDate startDate = LocalDate.of(2025, 2, 6);
		LocalDate endDate = LocalDate.of(2025, 2, 20);
		// 获取所有提交记录
		List<Commit> allCommits = new ArrayList<>();
		// 提前获取所有分支
		List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
//        for (Branch branch : branches) {
		// 获取该分支在时间范围内的所有提交
		java.util.Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.util.Date end = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Commit> branchCommits = gitLabApi.getCommitsApi().getCommits(project.getId(), "feature-fb", start, end);
		allCommits.addAll(branchCommits);
//        }

		// 按日期和作者分组（并行处理）
		Map<LocalDate, Map<String, List<Commit>>> commitsByDateAndAuthor = allCommits.parallelStream()
				.collect(Collectors.groupingBy(
						commit -> commit.getCommittedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
						Collectors.groupingBy(Commit::getAuthorName)));
		// 获取项目的成员列表
		List<Member> members = gitLabApi.getProjectApi().getMembers(project.getId());
		// 用于存储每个成员每天的统计信息
		Map<String, List<CodeContributionRecord>> memberStats = new HashMap<>();
		// 遍历成员列表
		for (Member member : members) {
			String name = member.getName();
			String state = member.getState();
			// 跳过不符合要求的用户
			if ("blocked".equals(state) || name.contains("root")) {
				continue;
			}
			// 遍历每天的提交信息
			for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
				Map<String, List<Commit>> commitsByAuthor = commitsByDateAndAuthor.getOrDefault(date, new HashMap<>());
				List<Commit> commits = commitsByAuthor.getOrDefault(name, Collections.emptyList());
				int addCode = 0;
				int delCode = 0;
				int totalCode = 0;
				// 统计成员的代码贡献
				for (Commit commit : commits) {
					// 过滤合并提交
					if (isMergeCommit(commit)) {
						continue;
					}
					// 这块是获取提交的详细信息， 提交信息集合中没有，只能每次获取详情
					Commit c = gitLabApi.getCommitsApi().getCommit(projectIdOrPath, commit.getShortId());
					addCode += c.getStats().getAdditions();
					delCode += c.getStats().getDeletions();
				}
				totalCode = (addCode - delCode);
				// 输出成员每天的代码修改统计信息
				log.info("时间：{}, 姓名：{}, 新增行数:{}, 删除行数:{}, 变更行数：{}", date, name, addCode, delCode, totalCode);
				// 存储统计信息
				memberStats.computeIfAbsent(name, k -> new ArrayList<>())
						.add(new CodeContributionRecord(date.toString(), addCode, delCode, totalCode));
			}
		}

		// 生成 Excel 文件
		generateExcel(memberStats, "D:\\" + projectIdOrPath + ".xlsx");
		// 记录结束时间
		long endTime = System.currentTimeMillis();
		// 计算执行时间
		long executionTime = endTime - startTime;
		System.out.println("逻辑执行时间: " + executionTime + " 毫秒");
	}

	// 判断是否为合并提交
	private static boolean isMergeCommit(Commit commit) {
		return commit.getParentIds() != null && commit.getParentIds().size() > 1;
	}

	private static void generateExcel(Map<String, List<CodeContributionRecord>> memberStats, String filePath)
			throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("代码贡献统计");

		int rowNum = 0;
		boolean isFirstMember = true;
		String[] headers = { "日期", "新增行数", "删除行数", "变更行数" };
		for (Map.Entry<String, List<CodeContributionRecord>> memberEntry : memberStats.entrySet()) {
			String memberName = memberEntry.getKey();
			List<CodeContributionRecord> records = memberEntry.getValue();

			if (!isFirstMember) {
				// 非第一个成员，空两行
				rowNum += 2;
			}
			isFirstMember = false;

			// 写入成员姓名
			Row nameRow = sheet.createRow(rowNum++);
			Cell nameCell = nameRow.createCell(0);
			nameCell.setCellValue("成员姓名: " + memberName);

			// 写入表头
			Row headerRow = sheet.createRow(rowNum++);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// 写入成员数据
			for (CodeContributionRecord record : records) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(record.date);
				row.createCell(1).setCellValue(record.addCode);
				row.createCell(2).setCellValue(record.delCode);
				row.createCell(3).setCellValue(record.totalCode);
			}
		}

		// 自动调整列宽
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// 保存 Excel 文件
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
		} finally {
			workbook.close();
		}

		log.info("Excel 文件已生成：{}", filePath);
	}

	/**
	 * 自定义类用于存储每天的代码贡献记录
	 */
	static class CodeContributionRecord {
		String date;
		int addCode;
		int delCode;
		int totalCode;

		CodeContributionRecord(String date, int addCode, int delCode, int totalCode) {
			this.date = date;
			this.addCode = addCode;
			this.delCode = delCode;
			this.totalCode = totalCode;
		}
	}

	public static void main(String[] args) throws Exception {
		ProjectApi projectApi = new ProjectApi();
		// 查询所有
//		projectApi.listProject();
		// 查询归属自己的仓库
//		projectApi.ownedProjects();
		// 获取星标项目
//		projectApi.starredProjects();
		// 查询项目成员
//		projectApi.memberProjects(420L);
		// 查询项目用户
//		projectApi.projectUsers(420L);
		// 创建仓库
//		projectApi.createProject();
		// 修改仓库
//		projectApi.updateProject(1365L);
		// 删除仓库
//		projectApi.deleteRepo(1365L);
		// 仓库归档
//		projectApi.archiveProject(1365L);
		// 解除归档
//		projectApi.unArchiveProject(1365L);
		// 统计代码行数
		// 指定仓库 ，遍历人员，查询所有人的代码修改量， 新增 ，删除，变更
//		projectApi.rowsStatistics("xuzhihao/spring");
		projectApi.rowsStatistics(417L);
	}
}
