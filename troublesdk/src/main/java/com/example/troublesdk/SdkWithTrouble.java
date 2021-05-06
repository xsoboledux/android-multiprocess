package com.example.troublesdk;

public class SdkWithTrouble {
     public static void runOperation() {

     }

     public static void runOperationWithTrouble() {
          throw new OutOfMemoryError();
     }
}
