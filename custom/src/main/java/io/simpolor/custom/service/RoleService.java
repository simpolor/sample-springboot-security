package io.simpolor.custom.service;

import io.simpolor.custom.repository.RoleRepository;
import io.simpolor.custom.repository.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;

	public List<Role> getAll() {
		return roleRepository.findAll();
	}

}
