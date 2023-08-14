package com.fontana.backend.template;

import com.fontana.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Integer> {

    Template[] getTemplatesByUser(User user);
}
