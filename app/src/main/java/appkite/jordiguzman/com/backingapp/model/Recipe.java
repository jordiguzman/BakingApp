package appkite.jordiguzman.com.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable{

    public String id;
    public String name;
    public ArrayList<Ingredients> ingredients= null;
    public ArrayList<Step> steps = null;
    private Integer servings;
    public String image;


    private Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        if (servings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(servings);
        }
        dest.writeString(image);
    }


}
