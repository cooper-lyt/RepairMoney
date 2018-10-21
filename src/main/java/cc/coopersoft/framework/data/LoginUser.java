package cc.coopersoft.framework.data;

public class LoginUser implements java.io.Serializable {


    private String useId;
    private String name;
    private String mail;
    private String photo;
    private String mobile;

    public LoginUser() {
    }

    public LoginUser(String useId) {
        this.useId = useId;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
