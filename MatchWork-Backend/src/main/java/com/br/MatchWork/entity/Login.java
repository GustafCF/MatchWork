package com.br.MatchWork.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.MatchWork.entity.dtos.LoginRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_LOGIN")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 4, max = 100, message = "The email must be between 4 and 100 characters long!")
    private String email;
    @Size(min = 8, max = 100, message = "The password must be between 8 and 100 characters long!")
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "login")
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "login")
    private Enterprise enterprise;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name="TB_ROLE_LOGIN",
        joinColumns = @JoinColumn(name = "login_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id") 
    )
    private List<Role> roles = new ArrayList<>();

    public Login() {}

    public Login(
            @Size(min = 4, max = 100, message = "The email must be between 4 and 100 characters long!") String email,
            @Size(min = 8, max = 100, message = "The password must be between 8 and 100 characters long!") String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean loginValdation(LoginRequestDto loginDto, BCryptPasswordEncoder encode) {
        return encode.matches(loginDto.password(), this.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Login other = (Login) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}