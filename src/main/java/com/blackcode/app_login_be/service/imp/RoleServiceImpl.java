package com.blackcode.app_login_be.service.imp;

import com.blackcode.app_login_be.model.Role;
import com.blackcode.app_login_be.payload.request.RoleRequest;
import com.blackcode.app_login_be.payload.response.RoleResponse;
import com.blackcode.app_login_be.repository.RoleRepository;
import com.blackcode.app_login_be.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getListRoleAllNotAdmin() {
        List<RoleResponse> rtnListAll = new ArrayList<>();
        List<Role> roleList = roleRepository.findAll();
        for (Role row : roleList){
            if(row.getRoleId() != 1){
                System.out.println("Data role List");
                System.out.println(row.getRoleId());
                System.out.println(row.getRoleName());
                RoleResponse rtnRow = new RoleResponse();
                rtnRow.setRoleId(row.getRoleId());
                rtnRow.setRoleName(row.getRoleName());
                rtnListAll.add(rtnRow);
            }
        }
        return rtnListAll;
    }

    @Override
    public List<RoleResponse> getListRoleAll() {
        List<RoleResponse> rtnListAll = new ArrayList<>();
        List<Role> roleList = roleRepository.findAll();
        for (Role row : roleList){
            System.out.println("Data role List");
            System.out.println(row.getRoleId());
            System.out.println(row.getRoleName());
            RoleResponse rtnRow = new RoleResponse();
            rtnRow.setRoleId(row.getRoleId());
            rtnRow.setRoleName(row.getRoleName());
            rtnListAll.add(rtnRow);
        }
        return rtnListAll;
    }

    @Override
    public RoleResponse getRoleById(Long roleId) {
        Optional<Role> dataRole = roleRepository.findById(roleId);
        if (dataRole.isPresent()){
            Role role = dataRole.get();
            RoleResponse rtnRole = new RoleResponse();
            rtnRole.setRoleId(role.getRoleId());
            rtnRole.setRoleName(role.getRoleName());
            return rtnRole;
        }
        return null;
    }

    @Override
    public RoleResponse createRole(RoleRequest roleReq) {
        Role role = new Role();
        role.setRoleName(roleReq.getRoleName());
        Role saveRole = roleRepository.save(role);
        RoleResponse rtnRole = new RoleResponse();
        rtnRole.setRoleId(saveRole.getRoleId());
        rtnRole.setRoleName(saveRole.getRoleName());
        return rtnRole;
    }

    @Override
    public RoleResponse updateRole(Long roleId, RoleRequest roleReq) {
        RoleResponse rtnRoleResponse = null;
                Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent()) {
            rtnRoleResponse = new RoleResponse();
            Role roleTemp = new Role();
            roleTemp.setRoleId(role.get().getRoleId());
            roleTemp.setRoleName(roleReq.getRoleName());
            Role roleUpdate = roleRepository.save(roleTemp);
            rtnRoleResponse.setRoleId(roleUpdate.getRoleId());
            rtnRoleResponse.setRoleName(roleUpdate.getRoleName());
            return rtnRoleResponse;
        }
        return null;
    }

    @Override
    public boolean deleteRole(Long roleId) {
        Optional<Role> roleData = roleRepository.findById(roleId);
        if(roleData.isPresent()){
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }
}
