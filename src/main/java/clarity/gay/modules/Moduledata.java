package clarity.gay.modules;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class Moduledata {

    @SerializedName("name")
    private final String name;

    @SerializedName("description")
    private final String description;

    @SerializedName("category")
    private final Category category;

    @Setter
    @SerializedName("keyCode")
    private int keyCode;

    @Setter
    @SerializedName("mode")
    private int currentMode;

    @Setter
    @SerializedName("enabled")
    private boolean enabled;

}