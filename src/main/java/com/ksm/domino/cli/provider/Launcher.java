package com.ksm.domino.cli.provider;

/**
 * Launcher to classload the libs inside this JAR. This is necessary because
 * BouncyCastle cannot be shaded.
 */
public class Launcher {

   public static void main(String[] args) {
      final JarClassLoader jcl = new JarClassLoader();
      try {
         jcl.invokeMain("com.ksm.domino.cli.Domino", args);
      } catch (final Throwable e) {
         e.printStackTrace();
      }
   }

}
