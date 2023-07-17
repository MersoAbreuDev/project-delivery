package com.example.project.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.project.entity.Usuario;
import com.example.project.requestDTO.UsuarioRequestDTO;
import com.example.project.responseDTO.PerfilResponseDTO;
import com.example.project.responseDTO.UsuarioResponseDTO;
import com.example.project.responseDTO.UsuarioTokenResponseDTO;
import com.example.project.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UsuarioService service;

    public JWTAutenticarFilter(AuthenticationManager authenticationManager, UsuarioService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsuarioRequestDTO usuarioRequestDTO = new ObjectMapper().readValue(request.getInputStream(),
                    UsuarioRequestDTO.class);

            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuarioRequestDTO.getLogin(), usuarioRequestDTO.getPassword(), new ArrayList<>()

            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o usuario" + e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();

        String token = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTConstants.TOKEN_EXPIRADO))
                .sign(Algorithm.HMAC512(JWTConstants.CHAVE_ASSINATURA));

        Usuario usuario = this.service.buscarUsuarioPeloLogin(user.getUsername());

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setLogin(usuario.getLogin());
        usuarioResponseDTO.setStatusUsuario(usuario.getStatusUsuario());

        PerfilResponseDTO perfilResponseDTO = new PerfilResponseDTO();
        perfilResponseDTO.setId(usuario.getPerfil().getId());
        perfilResponseDTO.setNome(usuario.getPerfil().getNome());
        usuarioResponseDTO.setPerfil(perfilResponseDTO);

        UsuarioTokenResponseDTO tokenResponseDTO = new UsuarioTokenResponseDTO();
        tokenResponseDTO.setUsuario(usuarioResponseDTO);
        tokenResponseDTO.setToken(token);

        String json = new Gson().toJson(tokenResponseDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();

    }

}