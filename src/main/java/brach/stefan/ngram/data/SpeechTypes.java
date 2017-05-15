package brach.stefan.ngram.data;

import java.util.HashSet;

/**
 * Wrapper object for google ngram data speech type endings.
 */
public class SpeechTypes {
    public static final HashSet<String> types = new HashSet<>();

    static {
        types.add("_ADP");
        types.add("_ADJ");
        types.add("_VERB");
        types.add("_NOUN");
        types.add("_PRON");
        types.add("_ADV");
        types.add("_CONJ");
        types.add("_DET");
        types.add("_PRT");
        // Unknown speech type.
        types.add("_X");
    }
}
