
import org.omg.CORBA.UNSUPPORTED_POLICY;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NoSuchElementException;


public class Main
{
    public static void main(String args[]){
        final Boolean q1 = Q1Test();
        final Boolean q2 = Q2Test();
        if (q1 == q2 == Boolean.TRUE) {
            System.out.println("Your tests is done. Make sure that you test all methods of class!! " );
            return;
        }
    }
    public static Boolean Q1Test(){

        NavigableMap<String,String> turkey = new BinaryNavMap<String,String>();
        turkey.put("uskudar","istanbul");
        turkey.put("kadıkoy","istanbul");
        turkey.put("cekirge","bursa");
        turkey.put("gebze","kocaeli");
        turkey.put("niksar","tokat");
        turkey.put("kecıoren","ankara");
        turkey.put("aksaray","istanbul");
        turkey.put("foca","izmir");
        turkey.put("manavgat","antalya");
        turkey.put("kahta","adıyaman");
        turkey.put("biga","canakkale");

        try
        {



            System.out.println("The entrySet is " +turkey.entrySet());
            System.out.println("The lowerEntry is " +turkey.lowerEntry("cekirge"));
            System.out.println("The lowerKey is " +turkey.lowerKey("cekirge"));
            System.out.println();

            System.out.println("The floorEntry is " +turkey.floorEntry("c"));
            System.out.println("The floorKey is " +turkey.floorKey("c"));
            System.out.println();

            /* floor equal test */
            System.out.println("The floorEntry(equal test) is " +turkey.floorEntry("cekirge"));
            System.out.println("The floorKey(equal test) is " +turkey.floorKey("cekirge"));
            System.out.println();

            System.out.println("The ceilingEntry is " +turkey.ceilingEntry("d"));
            System.out.println("The ceilingKey is " +turkey.ceilingKey("d"));
            System.out.println();

            System.out.println("The ceilingEntry(equal test) is " +turkey.ceilingEntry("foca"));
            System.out.println("The ceilingKey(equal test) is " +turkey.ceilingKey("foca"));
            System.out.println();

            System.out.println("The higherEntry is " +turkey.higherEntry("foca"));
            System.out.println("The higherEntry is " +turkey.higherKey("foca"));
            System.out.println();

            System.out.println("The FirstEntry is "+turkey.firstEntry());
            System.out.println();

            System.out.println("The LastEntry is "+turkey.lastEntry());
            System.out.println();

            System.out.println("The firstKey is "+turkey.firstKey());
            System.out.println();

            System.out.println("The lastKey is "+turkey.lastKey());
            System.out.println();


            System.out.println("The pollFirstEntry is "+turkey.pollFirstEntry());
            System.out.println();

            System.out.println("The pollLastEntry is "+turkey.pollLastEntry());
            System.out.println();


            System.out.println("The desMap is "+turkey.descendingMap());
            System.out.println();

            System.out.println("The navigableKeySet is "+turkey.navigableKeySet());
            System.out.println();

            System.out.println("The descendingKeySet is "+turkey.descendingKeySet());
            System.out.println();

            System.out.println("The headMap is "+turkey.headMap("uskudar",true));
            System.out.println();

            System.out.println("The tailMap is "+turkey.tailMap("manavgat"));
            System.out.println();

            System.out.println("The original set odds is " + turkey);
            NavigableMap<String,String> m = turkey.subMap("gebze",false,"uskudar",true);
            System.out.println("The ordered set m is " + m);
            System.out.println("The first entry is " +turkey.firstEntry());

        }
        catch (NullPointerException e) {
            System.out.println("NullPointer Exceptions : Variable Key can not be null");
        }
        catch (ClassCastException e){
            System.out.println("ClassCast Exceptions : param Key can not Compered key be null");
        }
        catch (NoSuchElementException e){
            System.out.println("NoSuchElementException : param Key can not Compered key be null");
        }
        catch (IllegalArgumentException e){
            System.out.println("IllegalArgumentException : fromKey | toKey");
        }
        catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException");
        }

        return Boolean.TRUE;

    }
    public static Boolean Q2Test(){
        HashMap<String,String> turkey=new HashTableChaining<String,String>();
        turkey.put("edremit","balikesir");
        turkey.put("edremit","van");
        turkey.put("kemalpasa","bursa");
        turkey.put("kemalpasa","izmir");
        turkey.put("ortakoy","istanbul");//we assume a district
        turkey.put("ortakoy","aksaray");
        turkey.put("ortakoy","corum");
        turkey.put("kecıoren","ankara");
        turkey.put("pinarbasi","kastamonu");
        turkey.put("pinarbasi","kayseri");
        turkey.put("eregli","konya");
        turkey.put("eregli","tekirdag");
        turkey.put("eregli","zonguldak");
        turkey.put("golbasi","adıyaman");
        turkey.put("golbasi","ankara");
        turkey.put("biga","canakkale");




        try {
            System.out.println(("\n ----- TEST2 --------- "));
            System.out.println("Turkey Size :" + turkey.size());
            System.out.println(turkey.get("eregli"));
            System.out.println(turkey.remove("eregli"));
            System.out.println(turkey.remove("eregli"));
            System.out.println(turkey.remove("eregli"));
            System.out.println(turkey.toString());
        }
        catch (NullPointerException e) {
            System.out.println("NullPointer Exceptions : Variable Key can not be null");
        }
        catch (ClassCastException e){
            System.out.println("ClassCast Exceptions : param Key can not Compered key be null");
        }
        catch (NoSuchElementException e){
            System.out.println("NoSuchElementException : param Key can not Compered key be null");
        }
        catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException");
        }
        return Boolean.TRUE;
    }


}
