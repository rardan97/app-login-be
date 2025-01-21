package com.blackcode.app_login_be.controller;

import com.blackcode.app_login_be.dto.RoleReq;
import com.blackcode.app_login_be.dto.RoleRes;
import com.blackcode.app_login_be.service.RoleService;
import com.blackcode.app_login_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRole")
    public ResponseEntity<List<RoleRes>> getAll(){
        return null;
    }

    @GetMapping("/getRoleById/{id}")
    public ResponseEntity<RoleRes> getRoleById(@PathVariable("id") Long id){
        return null;
    }

    @PostMapping("/createRole")
    public ResponseEntity<RoleRes> createRole(@RequestBody RoleReq roleReq){
        return null;
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<RoleRes> updateRole(@PathVariable("id") Long id, @RequestBody RoleReq roleReq){
        return null;
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<RoleRes> deleteRole(@PathVariable("id") Long id){
        return null;
    }
}
