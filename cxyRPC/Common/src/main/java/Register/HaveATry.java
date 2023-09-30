package Register;

public class HaveATry {
    public static void main(String[] args){
        Object[] objects = new Object[]{"hello",12,true};
        System.out.println(objects);
        for(Object object:objects)
            System.out.println(object.getClass());
    }
}
