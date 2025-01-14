package dev.studentstay.Documente.controller;

import dev.studentstay.Documente.jwt_security.GrpcClientService;
import dev.studentstay.Documente.service.RoleBasedLinksService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoleBasedLinksController {

    private final RoleBasedLinksService roleBasedLinksService;
    private final GrpcClientService grpcClientService;

    public RoleBasedLinksController(RoleBasedLinksService roleBasedLinksService, GrpcClientService grpcClientService) {
        this.roleBasedLinksService = roleBasedLinksService;
        this.grpcClientService = grpcClientService;
    }

    @GetMapping("/links")
    public List<Map<String, Object>> getLinks(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String role = grpcClientService.validateTokenAndGetRole(token);
        return roleBasedLinksService.getLinksForRole(role);
    }
}
