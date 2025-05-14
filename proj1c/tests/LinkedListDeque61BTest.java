import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BTest {
    @Test
    void test_iterator(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        List<String> list = new ArrayList<>();
        for (String s : lld1) {
            list.add(s);
        }
        assertThat(list).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void test_toString(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
    }
}
