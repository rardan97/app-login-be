package com.blackcode.app_login_be.controller;


import com.blackcode.app_login_be.model.Role;
import com.blackcode.app_login_be.payload.request.RoleRequest;
import com.blackcode.app_login_be.payload.response.RoleResponse;
import com.blackcode.app_login_be.service.RoleService;
import com.blackcode.app_login_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRole")
    public ResponseEntity<List<RoleResponse>> getAll(@RequestHeader("Authorization") String authHeader){
        List<RoleResponse> getListAll = roleService.getListRoleAll();
        return new ResponseEntity<>(getListAll, HttpStatus.OK);
    }

    @GetMapping("/getRoleById/{roleId}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable("roleId") Long id){
        RoleResponse rtnRole = roleService.getRoleById(id);
        return new ResponseEntity<>(rtnRole, HttpStatus.OK);
    }

    @PostMapping("/createRole")
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleReq){
        RoleResponse saveRole = roleService.createRole(roleReq);
        return new ResponseEntity<>(saveRole, HttpStatus.OK);
    }

    @PutMapping("/updateRole/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable("roleId") Long id, @RequestBody RoleRequest roleReq){
        RoleResponse updateRole = roleService.updateRole(id, roleReq);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseEntity<Map<String, String>> deleteRole(@PathVariable("roleId") Long id){
        boolean role = roleService.deleteRole(id);
        Map<String, String> rtn = new HashMap<>();
        if(role){
            rtn.put("status", "success");
            rtn.put("message", "role "+id+" delete success");

        }else{
            rtn.put("status", "failed");
            rtn.put("message", "role "+id+" delete failed");
        }
        return new ResponseEntity<>(rtn, HttpStatus.OK);
    }
}
