package pl.com.coders.shop2.mapper;

public interface IMapper<Entity,DTO>{

    Entity toEntity(DTO dto);
    DTO toDTO(Entity entity);

}
