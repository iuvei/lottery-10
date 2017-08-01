package com.wintv.framework.utils;


import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author Steady Wang
 * @version 2007-8-27 12:19:46
 */

public final class EncryptionUtil {
    private static Log logger = LogFactory.getLog(EncryptionUtil.class);

    private static String encoderText =
            "lsdfoglkwjemc-091324jlkmsda-0sd=1234;l;lsdkOPIER203-4LKJSLDJAS0D925JKNNC,MANSLDJQ32ELK1N4SAIp089er0234lk"
                    + "jo9df82l3kjlknf,nzxc,mn;lasdj9wquelq;d]qowe[;wq;qkwellsdkfj0-0POPOAR0W8RPOp-02@#$sdklj$#)0asdlksadLKJF"
                    + "A9820934)(&$3ij09sdj34-sdfj2po345-09dlkfjlkv,mxncv;laskdkl/a;au093hakjh2389~!@%&*%#&^539478(*&)^(&^_*8-*_+"
                    + "++|78w3ihsdnmnclksdj)(*#%*_@$(+#@$)&@#^*&^#@$()(*#@$HDFIkdhfgkjh098k;ldsk.sdv.c,msd;flkp0w34;2lk-=sd0p121o39"
                    + "-werl2k3;4lj09sdflskjlekfj,mv,mcxvjlksjdflksjdl*(#@!&akhduyqweperilmmdxcasnd*(#@9879327kjhasudfewr kwehriwue"
                    + "yrhc ausdgiq7w8e71 cdsh93ol2q32879y8932qwhdkjanhdskjaoe*&w#jh$)(*dsFshc na89wue32e981yewher12(*&#quds)(*i3o"
                    + "1928osaihdaklsdkalkduqowe3290874kljhklasdhlijhqweio4hwe89(*$#$eriho349oij(#*q$OIJHO)(&*#$_)(IUDSOIUoiOIUSAOD"
                    + "FU034liusdrogiuet0lsdmc,.mg;lq-091lk3l;kjsdf--123098fe*(JOKJSFD983345oihjdp0(#*$&#@!HKJH!(@#*&ioysdk@#)uhOA"
                    + "7E98R7239845K(*&(#@*$&HKFDJHWERYIWoi)(*&#@&^%@!dsfoi;.;,p[osklejr230897*(&we2&^%@78*(&#@!(7~&*~^@*&^#(*&auro"
                    + "iqkjwrhoasdf89qlrlkjpour09werk23jh";

    private EncryptionUtil() {
    }

    /**
     * Encrypt a string.
     *
     * @param input string needed to encrypt
     * @return encrypted string
     */
    public static String encrypt(String input) {
        if (input == null || input.trim().equals("")) {
            return input;
        }
        String result = "";
        try {
            Random rand = new Random(255);
            int seed = rand.nextInt() & 0xff;
            int pre = seed & 0x3;
            int len = input.length();
            int elen = encoderText.length();
            int i, j;

            result = encodeChar(seed);
            result = result + encodeChar((rand.nextInt() & 0xfc) + pre);
            for (i = 0; i < pre; i++) {
                result = result + encodeChar(rand.nextInt() & 0xff);
            }

            for (i = 0, j = seed; i < len; i++) {
                result = result + encodeChar(input.charAt(i) ^ encoderText.charAt(j));
                if (++j >= elen) {
                    j = 0;
                }
            }
        } catch (Exception e) {
            logger.error("unable to encrypt", e);
            throw new NestableRuntimeException("unable to encrypt", e);
        }
        return result;
    }

    /**
     * Decrypt a string.
     *
     * @param input string to decrypt
     * @return decrypted string
     */
    public static String decrypt(String input) {
        if (input == null || input.trim().equals("")) {
            return input;
        }
        StringBuffer result = new StringBuffer();
        try {
            String szSrcC = input.substring(0, 2);
            int seed = decodeChar(szSrcC);

            szSrcC = input.substring(2, 4);
            int pre = decodeChar(szSrcC);
            pre = pre & 3;

            int x;
            int i, j;
            int len = input.length();
            int elen = encoderText.length();

            for (i = pre + pre + 4, j = seed; i < len; i += 2) {
                szSrcC = input.substring(i, i + 2);
                x = decodeChar(szSrcC);
                x ^= encoderText.charAt(j);
                char ch = (char) x;
                result.append(ch);
                if (++j >= elen) {
                    j = 0;
                }
            }
        } catch (Exception e) {
            result = new StringBuffer("-1");
        }

        return result.toString();
    }

    private static String encodeChar(int c2) {
        int c = c2;
        String result;

        c &= 0xff;
        result = Integer.toHexString(c);
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    private static int decodeChar(String input) {
        int result = 0;
        //Now using a low efficiency way
        for (int i = 0; i < 256; i++) {
            if (input.equalsIgnoreCase(encodeChar(i))) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * One way hasing a string
     *
     * @param input
     * @return
     */
    public static String oneWayHash(String input) {
        String result = "";
        if (input == null || input.trim().equals("")) {
            return result;
        }
        byte[] i_str = input.getBytes();
        byte[] o_str;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            o_str = sha.digest(i_str);
            result = byte2hex(o_str);
        } catch (Exception e) {
            logger.error("unable to encrypt", e);
            throw new NestableRuntimeException("unable to do one way hashing", e);
        }

        return result;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = java.lang.Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            //	look up high nibble char
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);

            //	look up low nibble char
            sb.append(hexChar[b[i] & 0x0f]);
            sb.append(" ");
            if ((i != 0) && ((i % 15) == 0))
                sb.append("\n");
        }
        return sb.toString();
    }

    public static String toHexStringWithoutFormat(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            //	look up high nibble char
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);

            //	look up low nibble char
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) {
        System.out.println(EncryptionUtil.encrypt("72"));
        System.out.println(EncryptionUtil.decrypt("9d19095b41"));
    }
}


