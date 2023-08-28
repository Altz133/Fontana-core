package com.fontana.backend.template.repository;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Integer> {

    List<Template> getTemplatesByUser(User user);

    List<Template> getAllByStatusAndNameContainingOrderByUpdatedAtDesc(TemplateStatus status, String name, Pageable pageable);

    List<Template> getTemplatesByUserOrderByUpdatedAtDesc(User user, Pageable pageable);
    List<Template> getTemplatesByUserAndStatusOrderByUpdatedAt(User user,TemplateStatus status, Pageable pageable);
    List<Template> getTemplatesByUserAndStatusNotOrderByUpdatedAt(User user,TemplateStatus status, Pageable pageable);
}
