package com.forum.app.mapper.helper;

import com.forum.app.entity.Document;
import com.forum.app.entity.Role;
import com.forum.app.entity.User;
import com.forum.app.repository.RoleRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UserMapperHelper {
    private final RoleRepository roleRepository;

    public UserMapperHelper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Named("idToRole")
    public Role idToRole(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("userPhoto")
    public String getPhotoDocumentPath(User user) {
        if (user.getDocument() != null) {
            return user.getDocument().stream()
                    .filter(doc -> "USER_PHOTO".equals(doc.getDocumentType()))
                    .map(Document::getDocumentPath)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}