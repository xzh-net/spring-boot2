package net.xzh.gitlab.controller;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectUser;
import org.gitlab4j.api.models.Visibility;

/**
 * Project(项目) 相关操作
 *
 * 例如对任务的增、删、改、查等操作
 */
public class ProjectApi {

	/**
	 * GitLab远程主机地址
	 */
	public static final String GITLAB_REMOTE_URL = "http://172.17.17.136:18080";
	/**
	 * gitLab部署远程主机用户名
	 */
	public static final String GITLAB_REMOTE_USERNAME = "xuzhihao@163.com";

	/**
	 * GitLab远程主机密码（自己搭建的时，设计的密码）
	 */
	public static final String GITLAB_REMOTE_PWD = "12345678";
	
	private GitLabApi gitLabApi;

	ProjectApi(){
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
			List<Project> projects = gitLabApi.getProjectApi().getProjects(1,10);
			for (Project project : projects) {
	            System.out.println(project.getName());
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
			List<Project> projects = gitLabApi.getProjectApi().getOwnedProjects(1,10);
			for (Project project : projects) {
	            System.out.println(project.getName());
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
			List<Project> projects = gitLabApi.getProjectApi().getStarredProjects(1,10);
			for (Project project : projects) {
	            System.out.println(project.getName());
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
	            System.out.println(member.getName()+","+member.getAccessLevel());
	        }
		} catch (GitLabApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * 查询项目用户
     * @param projectId
     */

    public void projectUsers(Long projectId) {
        try {
        	List<ProjectUser> projectUsers = gitLabApi.getProjectApi().getProjectUsers(projectId);
        	for (ProjectUser projectUser : projectUsers) {
	            System.out.println(projectUser.getName());
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
     * 仓库重置
     * @param projectId
     */

	public static void main(String[] args) {
		ProjectApi projectApi = new ProjectApi();
		//查询所有
//		projectApi.listProject();
		//查询归属自己的仓库
//		projectApi.ownedProjects();
		//获取星标项目
//		projectApi.starredProjects();
		//查询项目成员
//		projectApi.memberProjects(1365L);
		//查询项目用户
//		projectApi.projectUsers(1365L);
		//创建仓库
//		projectApi.createProject();
		//修改仓库
//		projectApi.updateProject(1365L);
		//删除仓库
//		projectApi.deleteRepo(1365L);
		//仓库归档
//		projectApi.archiveProject(1365L);
		//解除归档
		projectApi.unArchiveProject(1365L);
	}

}
