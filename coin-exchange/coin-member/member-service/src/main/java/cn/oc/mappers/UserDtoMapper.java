package cn.oc.mappers;

import cn.oc.domain.User;
import cn.oc.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

     // 获取该对象的实例
    UserDtoMapper INSTANCE =  Mappers.getMapper(UserDtoMapper.class) ;

    /**
     * 将entity转化为dto
     * @param source
     * @return
     */
    UserDto convert2Dto(User source) ;

    /**
     * 将dto对象转化为entity对象
     * @param source
     * @return
     */
    User convert2Entity(UserDto source) ;


    /**
     * 将entity转化为dto
     * @param source
     * @return
     */
    List<UserDto> convert2Dto(List<User> source) ;

    /**
     * 将dto对象转化为entity对象
     * @param source
     * @return
     */
    List<User> convert2Entity(List<UserDto> source) ;
}