package com.example.sahmed.utilityapp.utility.device;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/07
 *     desc  : Shell related tools

 * </pre>
 */
public final class ShellUtils {

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Whether to execute the command under root
     *
     * @param command command
     * @param isRoot  Whether or not root privileges are required
     * @return CommandResult
     */
    public static CommandResult execCmd(final String command, final boolean isRoot) {
        return execCmd(new String[]{command}, isRoot, true);
    }

    /**
     * Whether to execute the command under root
     *
     * @param commands Multiple command lists
     * @param isRoot   Whether or not root privileges are required
     * @return CommandResult
     */
    public static CommandResult execCmd(final List<String> commands, final boolean isRoot) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * Whether to execute the command under root
     *
     * @param commands Multiple command arrays
     * @param isRoot   Whether or not root privileges are required

     * @return CommandResult
     */
    public static CommandResult execCmd(final String[] commands, final boolean isRoot) {
        return execCmd(commands, isRoot, true);
    }

    /**
     * Whether to execute the command under root
     *
     * @param command         command
     * @param isRoot          Whether or not root privileges are required
     * @param isNeedResultMsg Whether a result message is required

     * @return CommandResult
     */
    public static CommandResult execCmd(final String command, final boolean isRoot, final boolean isNeedResultMsg) {
        return execCmd(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * Whether to execute the command under root
     *
     * @param commands        Command list
     * @param isRoot          Whether or not root privileges are required
     * @param isNeedResultMsg Whether a result message is required

     * @return CommandResult
     */
    public static CommandResult execCmd(final List<String> commands, final boolean isRoot, final boolean isNeedResultMsg) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
    }

    /**
     * Whether to execute the command under root
     *
     * @param commands        Command array
     * @param isRoot          Whether or not root privileges are required
     * @param isNeedResultMsg Whether a result message is required

     * @return CommandResult
     */
    public static CommandResult execCmd(final String[] commands, final boolean isRoot, final boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // CloseUtils.closeIO(os, successResult, errorResult);
            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString()
        );
    }

    /**
     * Returns the result of the command
     */
    public static class CommandResult {
        /**
         * Result code
         **/
        public int    result;
        /**
         * Success information
         **/
        public String successMsg;
        /**
         * Error message
         **/
        public String errorMsg;

        public CommandResult(final int result, final String successMsg, final String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
