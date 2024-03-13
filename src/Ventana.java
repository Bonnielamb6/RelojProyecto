import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;

/*
 *TODO
 * implementar la fecha en un jlabel
 * implementar los numeros del reloj
 * */
public class Ventana extends JPanel implements Runnable {
    private final BufferedImage puerroSegundos;
    private final BufferedImage puerroMinutos;
    private final BufferedImage puerroHoras;
    private final BufferedImage fondo;
    private BufferedImage rotatedMinutos;
    private BufferedImage rotatedHoras;
    private BufferedImage rotatedSegundos;
    private int hora = -1;
    private int horaAnterior;
    private int minutoAnterior;
    private int min = -1;
    private int sec = -1;
    private int millis = -10;
    private int dia;
    private int mes;
    private int anio;

    public Ventana() {
        Dimension dimension = new Dimension(1000, 1000);
        setPreferredSize(dimension);
        Calendar calendar = Calendar.getInstance();

        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anio = calendar.get(Calendar.YEAR);
        minutoAnterior = -1;
        horaAnterior = -1;
        //Fondo del reloj

        fondo = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gfondo = (Graphics2D) fondo.getGraphics();
        gfondo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon iconoRelojFondo = new ImageIcon(getClass().getResource("img/clock_hatsune_miku.png"));
        Image img = iconoRelojFondo.getImage();
        gfondo.drawImage(img, 0, -50, 1000, 1000, null);
        Font fuente = new Font(gfondo.getFont().getName(), Font.PLAIN, 50);
        gfondo.setFont(fuente);
        gfondo.drawString("12", 465, 130);
        gfondo.drawString("3", 490 + 320, 130 + 330);
        gfondo.drawString("6", 480, 800);
        gfondo.drawString("9", 490 - 330, 130 + 330);
        gfondo.setStroke(new BasicStroke(10));
        //TODO dibujar las lineas en cada hora correctamente
        //linea de la 1
        //gfondo.drawLine(620,180,650,140);
        gfondo.drawString("1", 640, 175);
        //linea de las 2
        gfondo.drawString("2", 765, 285);
        //linea de las 4
        gfondo.drawString("4", 760, 640);
        //linea de las 5
        gfondo.drawString("5", 640, 745);
        //linea de las 7
        gfondo.drawString("7", 320, 735);
        //linea de las 8
        gfondo.drawString("8", 200, 625);
        //linea de las 10
        gfondo.drawString("10", 185, 290);
        //linea de las 11
        gfondo.drawString("11", 295, 175);
        gfondo.setColor(Color.BLACK);
        gfondo.fillRect(400, 580, 200, 50);
        gfondo.setColor(Color.WHITE);
        gfondo.setFont(fuente = new Font(gfondo.getFont().getName(), Font.PLAIN, 30));
        gfondo.drawString("" + dia + "/" + mes + "/" + anio, 435, 615);
        gfondo.dispose();

        //Minutero del reloj
        puerroMinutos = new BufferedImage(467, 350, BufferedImage.TYPE_INT_ARGB);
        Graphics gMinutos = puerroMinutos.getGraphics();
        ImageIcon iconoMinutos = new ImageIcon(getClass().getResource("img/leek_minutos.png"));
        Image imgMinutos = iconoMinutos.getImage();
        gMinutos.drawImage(imgMinutos, 0, 0, 467, 350, null);
        gMinutos.dispose();

        //Horario del reloj
        puerroHoras = new BufferedImage(276, 207, BufferedImage.TYPE_INT_ARGB);
        Graphics gHoras = puerroHoras.getGraphics();
        ImageIcon iconoHoras = new ImageIcon(getClass().getResource("img/leek_horas.png"));
        Image imgHoras = iconoHoras.getImage();    //350       200
        gHoras.drawImage(imgHoras, 0, 0, 276, 207, null);
        gHoras.dispose();

        //Segundero del reloj
        puerroSegundos = new BufferedImage(960, 720, BufferedImage.TYPE_INT_ARGB);
        Graphics gSegundos = puerroSegundos.getGraphics();
        ImageIcon iconoSegundos = new ImageIcon(getClass().getResource("img/leek.png"));
        Image imgSegundos = iconoSegundos.getImage();
        gSegundos.drawImage(imgSegundos, 0, 0, 960, 720, null);
        gSegundos.dispose();

        //Asignacion de los buffers
        rotatedMinutos = puerroMinutos;
        rotatedHoras = puerroHoras;
        rotatedSegundos = puerroSegundos;

        initComponents();
        Thread hilo = new Thread(this);
        hilo.start();
    }


    private void initComponents() {
        this.setLayout(null);
        JLabel lblTitulo = new JLabel("RELOJ ANALOGICO");
        lblTitulo.setBounds(420, 0, 200, 50);
        lblTitulo.setFont(new Font("arial", Font.PLAIN, 20));
        this.add(lblTitulo);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(fondo, 0, 0, null);
        update(g);
    }

    @Override
    public void update(Graphics g) {
        LocalDateTime time = LocalDateTime.now();
        Calendar calendar = Calendar.getInstance();
        sec = calendar.get(Calendar.SECOND);
        min = calendar.get(Calendar.MINUTE);
        hora = calendar.get(Calendar.HOUR);
        int currentMiliseconds = calendar.get(Calendar.MILLISECOND);
        //Validación inicial para darle la hora y el minuto correcto a las variables
        if (horaAnterior == -1) {
            horaAnterior = hora;
            minutoAnterior = min;
        }


        if (sec != Calendar.SECOND) {
            millis = -1;
            rotatedMinutos = rotateImage(puerroMinutos, (double) min * 360 / 60 + (double) (sec / 10));
            rotatedHoras = rotateImage(puerroHoras, (double) hora * 360 / 12 + (double) (min / 2));
        }

        if (currentMiliseconds > millis + 16) {
            double fractionOfSecond = (double) currentMiliseconds / 1000.0;
            double secondsAngle = (double) sec * 360.0 / 60.0 + fractionOfSecond * 6.0; // 360° / 60 segundos = 6° por segundo
            rotatedSegundos = rotateImage(puerroSegundos, secondsAngle);

        }

        //pintar los buffers
        g.drawImage(fondo, 0, 0, null);
        g.setClip(0, 0, getWidth(), getHeight());
        g.drawImage(rotatedSegundos, 10, 80, null);
        g.drawImage(rotatedMinutos, 255, 267, null);
        g.drawImage(rotatedHoras, 350, 335, null);



        //sonar cancion cada que cambia la hora
        if (horaAnterior != hora) {
            reproducirMusica("img/miku_by_anamanaguchi.wav");
            horaAnterior = hora;
        }


        //Sonar cancion cada que cambia el minuto
        if (minutoAnterior != min) {
            reproducirMusica("img/miku_by_anamanaguchi.wav");
            minutoAnterior = min;
        }


        //TODO implementar sonido cada que cambia el segundo
        if (sec % 2 == 0) {
            //tocar cancion donde es mi

        } else {
            //tocar cancion donde es ku

        }
    }

    private void reproducirMusica(String filePath) {
        new Thread(() -> {
            try {

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Ventana.class.getResource(filePath));
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                Clip clip = (Clip) AudioSystem.getLine(info);

                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void run() {
        while (true) {
            //Graphics2D g2 = (Graphics2D) puerroHoras.getGraphics();
            //Graphics2D g4 = (Graphics2D) fondo.getGraphics();
            //Graphics2D g3 = (Graphics2D) puerroMinutos.getGraphics();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
    }

    private BufferedImage rotateImage(BufferedImage img, double angulo) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, img.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angulo), width / 2, height / 2);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();
        return rotatedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reloj Proyecto");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Ventana());
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
