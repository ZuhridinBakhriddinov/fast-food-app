package uz.pdp.fastfoodapp.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.fastfoodapp.entity.user.User;

@Data

@AllArgsConstructor

@NoArgsConstructor

public class ResponseToken {

    private String accessToken;

    private String refreshToken;

    private User user;


    public ResponseToken(String accessToken, String refreshToken) {

        this.accessToken = accessToken;

        this.refreshToken = refreshToken;

    }

}
