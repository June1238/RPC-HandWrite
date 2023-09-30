package Impl;

import API.MoonLight;

public class MoonImpl_1 implements MoonLight {
    @Override
    public String HappyMoon(int id) {
        return "happy moon "+ id;
    }

    //实现moon
    @Override
    public String LoveMoon(String moon_name) {
        return "I love moon of " + moon_name;
    }
}
