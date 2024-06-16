package votacao.backend.utils;

public enum RolesEnum {
    COMMON("COMMON"),
    ADMIN("ADMIN");

    private final String roleName;

    RolesEnum(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
