package site.achun.lofter.service;

public class LofterUserServiceTest {

    public static void main(String[] args) {
        String url = "https://sakalee.lofter.com/";
        LofterUserService service = new LofterUserService(url,null);
        System.out.println(service.getUserCode());
    }
}
