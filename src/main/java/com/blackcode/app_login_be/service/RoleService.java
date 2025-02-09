package com.blackcode.app_login_be.service;

import com.blackcode.app_login_be.payload.request.RoleRequest;
import com.blackcode.app_login_be.payload.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<RoleResponse> getListRoleAllNotAdmin();

    List<RoleResponse> getListRoleAll();


    RoleResponse getRoleById(Long roleId);

    RoleResponse createRole(RoleRequest roleReq);

    RoleResponse updateRole(Long roleId, RoleRequest roleReq);

    boolean deleteRole(Long roleId);

}
