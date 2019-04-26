import elgamal.*;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        importPublicKeyButton.addActionListener(e -> importCipherKey());
        exportPrivateKeyButton.addActionListener(e -> exportCipherKey(false));
        importPrivateKeyButton.addActionListener(e -> importCipherKey());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
    }

    public void inputFileDialog() {
        inputChooser = new JFileChooser();
        int returnValue = inputChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = inputChooser.getSelectedFile().getPath();
            try {
//                blocks = DataUtils.loadFile(selectedFile);
//                String message = blocks.length + " data blocks have been loaded.";
//                log("File " + inputChooser.getSelectedFile().getName() + " loaded.");
//                log(message);
//                TODO: Implement file loading
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
//        String input = inputTextArea.getText();
//        blocks = DataUtils.loadText(input);
//        log("Text has been loaded.");
//        log(blocks.length + " data blocks have been loaded.");
//        TODO: Implement text loading
        loadedFromFile = false;
        updateButtons();
    }

    private void importCipherKey() {
        JFileChooser keyChooser = new JFileChooser();
        int returnValue = keyChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = keyChooser.getSelectedFile().getPath();
            try {
                byte[] bytes = DataUtils.loadBytes(selectedFile);
//                TODO: Update cipher key import conditions
                if (bytes.length == 16) {
//                    key = new Key(bytes);
//                    updateCipherKey(new String(bytes));
//                    log("Cipher key imported.");
//                    TODO: Implement cipher key import
                } else {
                    JOptionPane.showMessageDialog(frame,"Cipher key must have exactly 16 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                String message = "Could not load file: " + selectedFile;
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Loading error", JOptionPane.ERROR_MESSAGE);
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
//            Encryption encryption = new Encryption(blocks, key);
//            TODO: Uncomment encryption instance
            log("Starting encryption...");
//            encryption.encrypt();
//            TODO: Uncomment encrypt action
            log("Encryption completed successfully.");

            if (loadedFromFile) {
                JFileChooser keyChooser = new JFileChooser();
                int returnValue = keyChooser.showSaveDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                    try {
//                        DataUtils.saveFile(encryption.getBlocks(), selectedPath);
//                        TODO: Implement encrypted file save
                        log("Encrypted data saved: " + selectedPath);
                    } catch (Exception ex) {
                        String message = "Could not save file: " + selectedPath;
                        log(message);
                        JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
//                outputTextArea.setText(DataUtils.saveText(encryption.getBlocks()));
//                TODO: Implement encrypted text display
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
//            Decryption decryption = new Decryption(blocks, key);
//            TODO: Uncomment decryption instance
            log("Starting decryption...");
//            decryption.decrypt();
//            TODO: Uncomment decrypt action
            log("Decryption completed successfully.");

            if (loadedFromFile) {
                JFileChooser keyChooser = new JFileChooser();
                int returnValue = keyChooser.showSaveDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                    try {
//                        DataUtils.saveFile(decryption.getBlocks(), selectedPath);
//                        TODO: Implement encrypted file save
                        log("Decrypted data saved: " + selectedPath);
                    } catch (Exception ex) {
                        String message = "Could not save file: " + selectedPath;
                        log(message);
                        JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
//                outputTextArea.setText(DataUtils.saveText(decryption.getBlocks()));
//                TODO: Implement decrypted text display
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
//        TODO: Implement update buttons method
//        if (blocks != null && blocks.length > 0 && key != null) {
//            canProcess = true;
//        } else {
//            canProcess = false;
//        }
        _updateButtons();
    }

    private void _updateButtons() {
        encryptButton.setEnabled(canProcess);
        decryptButton.setEnabled(canProcess);
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
