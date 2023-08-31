package com.fontana.backend.template.repository;

import com.fontana.backend.template.entity.Template;
import com.fontana.backend.template.entity.TemplateStatus;
import com.fontana.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Integer> {
    //public templates
    Page<Template> getTemplatesByStatusAndNameContainsOrderByNameAsc(TemplateStatus status, String name, Pageable pageable);

    //my templates
    Page<Template> getTemplatesByStatusNotAndUserOrderByNameAsc(TemplateStatus status1, User user, Pageable pageable);

    //snippet my templates
    List<Template> getTemplatesByUserAndStatusNotAndStatusNotOrderByUpdatedAtDesc(User user, TemplateStatus status1, TemplateStatus status2, Pageable pageable);

    //snippet editing tool
    List<Template> getTemplatesByUserAndStatusOrderByUpdatedAtDesc(User user, TemplateStatus status, Pageable pageable);
}
