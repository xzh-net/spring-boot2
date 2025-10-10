package net.xzh.sqlite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.xzh.sqlite.model.User;
import net.xzh.sqlite.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User getUserByID(Long id) {
		return userRepository.findById(id).get();
	}

	public User getByName(String name) {
		return userRepository.findByName(name);
	}

	public Page<User> findPage() {
		Pageable pageable = PageRequest.of(0, 10);
		return userRepository.findAll(pageable);
	}

	public Page<User> find(Long maxId) {
		Pageable pageable = PageRequest.of(0, 10);
		return userRepository.findMore(maxId, pageable);
	}

	public User save(User u) {
		return userRepository.save(u);
	}

	public User update(Long id, String name) {
		User user = userRepository.findById(id).get();
		user.setName(name);
		return userRepository.save(user);
	}

	public Boolean resetPwd(String name, String salt, Long id) {
		return userRepository.resetPwd(name, salt, id) == 1;
	}

}