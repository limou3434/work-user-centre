declare namespace API {
  type BaseResponseBoolean = {
    code?: number;
    message?: string;
    data?: boolean;
  };

  type BaseResponseListLoginUserVO = {
    code?: number;
    message?: string;
    data?: LoginUserVO[];
  };

  type BaseResponseLoginUserVO = {
    code?: number;
    message?: string;
    data?: LoginUserVO;
  };

  type BaseResponseLong = {
    code?: number;
    message?: string;
    data?: number;
  };

  type LoginUserVO = {
    id?: number;
    userAccount?: string;
    userWxUnion?: string;
    userMpOpen?: string;
    userEmail?: string;
    userPhone?: string;
    userAvatar?: string;
    userTags?: string;
    userNick?: string;
    userName?: string;
    userProfile?: string;
    userBirthday?: string;
    userCountry?: string;
    userAddress?: string;
    userRole?: number;
    userLevel?: number;
    userGender?: number;
  };

  type UserAddRequest = {
    userAccount?: string;
    userPasswd?: string;
    userAvatar?: string;
    userRole?: string;
  };

  type UserDeleteRequest = {
    id?: number;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPasswd?: string;
  };

  type UserRegisterRequest = {
    userAccount?: string;
    userPasswd?: string;
    checkPasswd?: string;
  };

  type UserSearchRequest = {
    sortField?: string;
    sortOrder?: string;
    id?: number;
    userAccount?: string;
    userRole?: number;
    userLevel?: number;
  };

  type UserUpdateRequest = {
    id?: number;
    userAccount?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: number;
    userLevel?: number;
    userGender?: number;
  };
}
