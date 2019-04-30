import elgamal.*;
import elgamal.Number;
import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
//    Frame
    JFrame frame;
//    Main panel
    public JPanel mainPanel;

    private JButton inputFile;
    private JButton importPublicKeyButton;
    private JTextArea log;
    private JLabel infoInputFile;
    private JLabel infoBlocksCount;
    private JTextArea publicKeyTextarea;
    private JButton exportPublicKeyButton;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton inputText;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton importPrivateKeyButton;
    private JButton exportPrivateKeyButton;
    private JTextArea privateKeyTextarea;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    Model
    private byte[] data;
    private JFileChooser inputChooser;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private boolean canProcess = false;
    private boolean loadedFromFile = false;

    public Application(JFrame frame) {
        this.frame = frame;
        createMenu();

        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

//        Buttons
        setIcon(inputFile, "file_in.png");
        setIcon(inputText, "keyboard.png");
        setIcon(importPublicKeyButton, "cipher_key.png");
        setIcon(exportPublicKeyButton, "cipher_key.png");
        setIcon(importPrivateKeyButton, "cipher_key.png");
        setIcon(exportPrivateKeyButton, "cipher_key.png");

        encryptButton.setEnabled(canProcess);
        decryptButton.setEnabled(canProcess);

//        Actions
        inputFile.addActionListener(e -> inputFileDialog());
        inputText.addActionListener(e -> inputTextDialog());
        exportPublicKeyButton.addActionListener(e -> exportCipherKey(true));
        importPublicKeyButton.addActionListener(e -> importCipherKey(true));
        exportPrivateKeyButton.addActionListener(e -> exportCipherKey(false));
        importPrivateKeyButton.addActionListener(e -> importCipherKey(false));
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
    }

    public void inputFileDialog() {
        inputChooser = new JFileChooser();
        int returnValue = inputChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = inputChooser.getSelectedFile().getPath();
            try {
                data = DataUtils.loadBytes(selectedFile);
                System.out.println("IN: " + Arrays.toString(data));

                String message = data.length + " bytes have been loaded.";
                log("File " + inputChooser.getSelectedFile().getName() + " loaded.");
                log(message);
                infoInputFile.setText(inputChooser.getSelectedFile().getName());
                updateButtons();
                loadedFromFile = true;
            } catch (Exception ex) {
                String message = "Could not load file: " + selectedFile;
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Loading error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void inputTextDialog() {
        String input = inputTextArea.getText();
        data = input.getBytes();
        System.out.println("IN: " + Arrays.toString(data));
        log("Text has been loaded.");
        log(data.length + " bytes have been loaded.");
        loadedFromFile = false;
        updateButtons();
    }

    private void importCipherKey(boolean type) {
//        true - public, false - private
        String pattern_string = (type ? PublicKey.getPattern() : PrivateKey.getPattern());

        JFileChooser keyChooser = new JFileChooser();
        int returnValue = keyChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = keyChooser.getSelectedFile().getPath();
            try {
                byte[] bytes = DataUtils.loadBytes(selectedFile);
                String key_string = new String(bytes);
                Pattern pattern = Pattern.compile(pattern_string);
                Matcher matcher = pattern.matcher(key_string);
                boolean valid = matcher.matches();
                if (!valid) {
                    throw new Exception("Invalid key");
                }

                if (type) {
                    Number p = new Number(matcher.group(1));
                    Number g = new Number(matcher.group(2));
                    Number h = new Number(matcher.group(3));
                    publicKey = new PublicKey(p, g, h);
                    log("Public key imported.");
                } else {
                    Number a = new Number(matcher.group(1));
                    Number p = new Number(matcher.group(2));
                    privateKey = new PrivateKey(a, p);
                    log("Private key imported.");
                }

                updateKeys();

            } catch (IOException ex) {
                String message = "Could not load file: " + selectedFile;
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Loading error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Loading error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportCipherKey(boolean type) {
//        true - public, false - private
        Key key = (type ? publicKey : privateKey);
        if (key != null) {
            JFileChooser keyChooser = new JFileChooser();
            int returnValue = keyChooser.showSaveDialog(mainPanel);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                try {
                    DataUtils.saveBytes(key.getBytes(), selectedPath);
                    log("Cipher key exported.");
                } catch (Exception ex) {
                    String message = "Could not save file: " + selectedPath;
                    log(message);
                    JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame,"Key doesn't exists. Please first import key.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateKey() {
        String number = JOptionPane.showInputDialog("Key length [5-400]: ");
        try {
            int length = Integer.parseInt(number);
            if (length < 5 || length > 400) {
                throw new Exception("Invalid key length (only in range 5 - 400).");
            }
            KeyGenerator keygen = new KeyGenerator(length);
            keygen.generate();
            publicKey = keygen.getPublicKey();
            log("Public key generated.");
            privateKey = keygen.getPrivateKey();
            log("Private key generated.");
            updateKeys();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,"Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateKeys() {
        if (publicKey != null) {
            publicKeyTextarea.setText(publicKey.toString());
        }
        if (privateKey != null) {
            privateKeyTextarea.setText(privateKey.toString());
        }
        updateButtons();
    }

    private void encrypt() {
        if (publicKey != null) {
            canProcess = false;
            _updateButtons();

            log("Preparing encryption...");
            Block[] blocks = Operations.generateBlocks(data, publicKey.getMaxLength());
            Encryption encryption = new Encryption(blocks, publicKey);
            log("Starting encryption...");
            encryption.encrypt();
            log("Encryption completed successfully.");

            System.out.println(Arrays.toString(data));
            System.out.println("E Blocks in:");
            for (Block b : encryption.getResults()) {
                System.out.println(Arrays.toString(b.getData()));
            }
            System.out.println("E IN BLOCKS COUNT: " + blocks.length + " / " + blocks[0].getData().length);

            byte[] bytes = Operations.blocksToBytes(encryption.getResults(), publicKey.getFillSize());
            System.out.println("CHUJ --- " + Arrays.toString(bytes));

            if (loadedFromFile) {
                JFileChooser keyChooser = new JFileChooser();
                int returnValue = keyChooser.showSaveDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                    try {
                        DataUtils.saveBytes(bytes, selectedPath);
                        log("Encrypted data saved: " + selectedPath);
                    } catch (Exception ex) {
                        String message = "Could not save file: " + selectedPath;
                        log(message);
                        JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                System.out.println("E Blocks out:");
                for (Block b : encryption.getResults()) {
                    System.out.println(Arrays.toString(b.getData()));
                }
                System.out.println("E OUT BLOCKS COUNT: " + encryption.getResults().length+ " / " + encryption.getResults()[0].getData().length);
                outputTextArea.setText(new String(bytes));
            }
            canProcess = true;
            _updateButtons();
        }
    }

    private void decrypt() {
        if (privateKey != null) {
            canProcess = false;
            _updateButtons();

            log("Preparing decryption...");
            System.out.println(Arrays.toString(data));
            Block[] blocks = Operations.generateBlocks(outputTextArea.getText().getBytes(), privateKey.getFillSize());
            System.out.println("KURWA " + blocks.length + " / " + blocks[0].getData().length);
            System.out.println("D Blocks in:");
            for (Block b : blocks) {
                System.out.println(Arrays.toString(b.getData()));
            }
            Decryption decryption = new Decryption(blocks, privateKey);
            log("Starting decryption...");

            try {
                decryption.decrypt();
                log("Decryption completed successfully.");
                System.out.println("D IN BLOCKS COUNT: " + blocks.length+ " / " + blocks[0].getData().length);
                byte[] bytes = Operations.blocksToBytes(decryption.getResults(), privateKey.getMaxLength());


                if (loadedFromFile) {
                    JFileChooser keyChooser = new JFileChooser();
                    int returnValue = keyChooser.showSaveDialog(mainPanel);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                        try {
                        DataUtils.saveBytes(bytes, selectedPath);
                            log("Decrypted data saved: " + selectedPath);
                        } catch (Exception ex) {
                            String message = "Could not save file: " + selectedPath;
                            log(message);
                            JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    System.out.println("D Blocks out:");
                    for (Block b : decryption.getResults()) {
                        System.out.println(Arrays.toString(b.getData()));
                    }
                    outputTextArea.setText(new String(bytes));
                    System.out.println("D OUT BLOCKS COUNT: " + decryption.getResults().length+ " / " + decryption.getResults()[0].getData().length);
                }
            } catch (CorruptedDataException e) {
                String message = "Corrupted data.";
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Decryption error", JOptionPane.ERROR_MESSAGE);
            }

            canProcess = true;
            _updateButtons();
        }
    }

    private void setIcon(JButton button, String path) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon imgIcon = new ImageIcon(img);
            button.setIcon(imgIcon);
        } catch (Exception ex) {
//            ...
            System.out.println(ex);
        }
    }

    private void updateButtons() {
        _updateButtons();
    }

    private void _updateButtons() {
        if (data != null && data.length > 0) {
            encryptButton.setEnabled(publicKey != null);
            decryptButton.setEnabled(privateKey != null);
        } else {
            encryptButton.setEnabled(false);
            decryptButton.setEnabled(false);
        }
    }

    private void log(String message) {
        Date date = new Date();
        log.append('[' + dateFormat.format(date) + "]: " + message + '\n');
    }

    public void dispose() {}

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

//        File
        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem file_item_1 = new JMenuItem("Load file");
        file_item_1.addActionListener(e -> inputFileDialog());

        file.add(file_item_1);

//        Key
        JMenu key = new JMenu("Key");
        menuBar.add(key);

        JMenuItem key_item_1 = new JMenuItem("Generate key");
        key_item_1.addActionListener(e -> generateKey());

        key.add(key_item_1);

        frame.setJMenuBar(menuBar);
    }
}
