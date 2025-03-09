declare namespace API {
  type searchUsersParams = {
    userName?: string;
  };

  type User = {
    id?: number;
    userAccount?: string;
    userWxUnion?: string;
    userMpOpen?: string;
    userEmail?: string;
    userPhone?: string;
    userIdent?: string;
    userPasswd?: string;
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
    deleted?: number;
    createTime?: string;
    updateTime?: string;
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
}
