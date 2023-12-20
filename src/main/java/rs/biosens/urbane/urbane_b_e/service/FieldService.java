package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Field;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.FieldDTO;
import rs.biosens.urbane.urbane_b_e.repos.FieldRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;


@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    public FieldService(final FieldRepository fieldRepository,
            final UserRepository userRepository) {
        this.fieldRepository = fieldRepository;
        this.userRepository = userRepository;
    }

    public List<FieldDTO> findAll() {
        final List<Field> fields = fieldRepository.findAll(Sort.by("id"));
        return fields.stream()
                .map(field -> mapToDTO(field, new FieldDTO()))
                .toList();
    }

    public FieldDTO get(final Integer id) {
        return fieldRepository.findById(id)
                .map(field -> mapToDTO(field, new FieldDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FieldDTO fieldDTO) {
        final Field field = new Field();
        mapToEntity(fieldDTO, field);
        return fieldRepository.save(field).getId();
    }

    public void update(final Integer id, final FieldDTO fieldDTO) {
        final Field field = fieldRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fieldDTO, field);
        fieldRepository.save(field);
    }

    public void delete(final Integer id) {
        fieldRepository.deleteById(id);
    }

    private FieldDTO mapToDTO(final Field field, final FieldDTO fieldDTO) {
        fieldDTO.setId(field.getId());
        fieldDTO.setName(field.getName());
        fieldDTO.setLat(field.getLat());
        fieldDTO.setLongg(field.getLongg());
        fieldDTO.setCropType(field.getCropType());
        fieldDTO.setHbm(field.getHbm());
        fieldDTO.setCreatedAt(field.getCreatedAt());
        fieldDTO.setUsers(field.getUsers() == null ? null : field.getUsers().getId());
        return fieldDTO;
    }

    private Field mapToEntity(final FieldDTO fieldDTO, final Field field) {
        field.setName(fieldDTO.getName());
        field.setLat(fieldDTO.getLat());
        field.setLongg(fieldDTO.getLongg());
        field.setCropType(fieldDTO.getCropType());
        field.setHbm(fieldDTO.getHbm());
        field.setCreatedAt(fieldDTO.getCreatedAt());
        final User users = fieldDTO.getUsers() == null ? null : userRepository.findById(fieldDTO.getUsers())
                .orElseThrow(() -> new NotFoundException("users not found"));
        field.setUsers(users);
        return field;
    }

}
