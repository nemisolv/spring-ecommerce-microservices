package net.nemisolv.identity.security.oauth2.user;

import java.util.Map;

public class AzureMicrosoftOAuth2UserInfo extends OAuth2UserInfo{
    public AzureMicrosoftOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImageUrl() {

        return (String) attributes.get("picture");
    }
}