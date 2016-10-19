package ge.gita.restexample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by irakli.managadze on 10/19/2016
 */

public class Country {

    @SerializedName("name")
    private String name;

    @SerializedName("alpha2_code")
    private String alpha2Code;

    @SerializedName("alpha3_code")
    private String alpha3Code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }
}
