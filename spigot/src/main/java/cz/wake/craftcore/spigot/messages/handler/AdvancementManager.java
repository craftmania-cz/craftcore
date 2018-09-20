package cz.wake.craftcore.messages.handler;

import com.google.gson.JsonObject;
import cz.wake.craftcore.messages.Advancement;

import java.util.HashMap;

public class AdvancementManager {

    static HashMap<String, Advancement> map;

    static {
        AdvancementManager.map = new HashMap<String, Advancement>();
    }

    public static Advancement getOld(final String... array) {
        String string = "";
        for (int length = array.length, i = 0; i < length; ++i) {
            string = String.valueOf(string) + array[i];
        }
        return AdvancementManager.map.get(string);
    }

    public static void add(final Advancement cmiAdvancement) {
        AdvancementManager.map.put(cmiAdvancement.getId().getKey(), cmiAdvancement);
    }

    public enum FrameType
    {
        TASK("TASK", 0, "task"),
        GOAL("GOAL", 1, "goal"),
        CHALLENGE("CHALLENGE", 2, "challenge");

        private String name;

        private FrameType(final String s, final int n, final String name) {
            this.name = name;
        }

        public static FrameType getFromString(final String s) {
            try {
                FrameType[] values;
                for (int length = (values = values()).length, i = 0; i < length; ++i) {
                    final FrameType frameType = values[i];
                    if (frameType.name.equalsIgnoreCase(s)) {
                        return frameType;
                    }
                }
            }
            catch (EnumConstantNotPresentException ex) {}
            return FrameType.TASK;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public class Condition
    {
        protected String name;
        protected JsonObject set;

        public Condition(final String name, final JsonObject set) {
            this.name = name;
            this.set = set;
        }
    }

    public class ConditionBuilder
    {
        private String name;
        private JsonObject set;

        public Condition build() {
            return new Condition(this.name, this.set);
        }
    }
}
