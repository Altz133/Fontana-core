package com.fontana.backend.template.repository;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Integer> {

    Template[] getTemplatesByUser(User user);

    Template[] getAllByStatus(TemplateStatus status);
}
