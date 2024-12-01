package com.forum.app.mapper.helper;

import com.forum.app.entity.*;
import com.forum.app.repository.CityRepository;
import com.forum.app.repository.CountryRepository;
import com.forum.app.repository.RoleRepository;
import com.forum.app.utils.Utility;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UserMapperHelper {
    private final RoleRepository roleRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final Utility utility;

    public UserMapperHelper(RoleRepository roleRepository,
                            CityRepository cityRepository, Utility utility,
                            CountryRepository countryRepository) {
        this.roleRepository = roleRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.utility = utility;
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

    @Named("idToCity")
    public City idToCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("idToCountry")
    public Country idToCountry(Long countryId) {
        return countryRepository.findById(countryId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("encodePassword")
    public String encodePassword(String password) {
        return utility.encryptPassword(password);
    }
}