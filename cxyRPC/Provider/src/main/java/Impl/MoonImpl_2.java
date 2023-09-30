package Impl;

import API.MoonLight;

public class MoonImpl_2 implements MoonLight {
    @Override
    public String LoveMoon(String moon_name) {
        return moon_name + " is so lovely!";
    }

    @Override
    public String HappyMoon(int id) {
        return "This is Delight-Moon "+ id;
    }
}
