package com.javaprogramming;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Main
{
    public static int pageNo = 0;
    public static int fileNo = 0;
    public static String base_url = "https://pec.ac.in";

    public static String rectify(String link)
    {
        // ignore links starting with #
        if(link.startsWith("#"))
            return base_url;

        // add base address if link starts with /
        if(link.startsWith("/"))
            return base_url + link;

        return link;
    }

    public static boolean isValid(String link)
    {
        String s1 = "https://alumni.pec.ac.in";
        String s2 = "https://pec.ac.in";
        String s3 = "http://pec.ac.in";
        String s4 = "https://www.pec.ac.in";
        String s5 = "http://www.pec.ac.in";

        return link.startsWith(s1) || link.startsWith(s2) || link.startsWith(s3) || link.startsWith(s4) || link.startsWith(s5);

    }

    public static boolean hasFaculty(String url, String para)
    {
        String f1 = "faculty";
        String f2 = "Faculty";
        String f3 = "FACULTY";

        boolean b1 = url.contains(f1) || url.contains(f2) || url.contains(f3);
        boolean b2 = para.contains(f1) || para.contains(f2) || para.contains(f3);

        return b1 || b2;
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException
    {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static void parse(int depth, ArrayList<String> local_urls, ArrayList<String> final_urls, ArrayList<String> final_paras,
                             ArrayList<String> faculty_urls, ArrayList<String> faculty_paras, Set<String> visited_urls)
    {
        //If depth is 5, return
        if(depth==5) return;

        //Traversing each link in the local_urls
        for(int i=0; i<local_urls.size(); i++)
        {
            //Current url
            String url = local_urls.get(i);

            try
            {
                final Document document = Jsoup.connect(url).get();

                Elements paragraphs = document.select("p");

                //Extracting paragraph from the current url
                String para = "";

                for(Element p : paragraphs)
                    para = para.concat(p.text());

                // general crawler
                final_urls.add(url);
                final_paras.add(para);

                // focused crawler
                if(hasFaculty(url, para))
                {
                    faculty_urls.add(url);
                    faculty_paras.add(para);
                }

                // get all links in page
                Elements links = document.select("a[href]");

                ArrayList<String> local_urls_new = new ArrayList<String>();

                for (Element link : links)
                {
                    // get the value from the href attribute
                    String new_link = link.attr("href");

                    // rectify the new link
                    new_link = rectify(new_link);

                    // check if the link is valid
                    if(!isValid(new_link)) continue;

                    // check if the link is garbage
                    if(new_link.equals("") || new_link.endsWith("javascript:;") ||
                            new_link.contains("https://pec.ac.in/~pecac") || new_link.endsWith("annexure-III"))
                        continue;

                    // if the link is already visited, continue
                    if(visited_urls.contains(new_link)) continue;

                    // if the link is downloadable
                    if(new_link.contains(".pdf") || new_link.contains(".PDF") || new_link.contains(".xlsx") ||
                            new_link.contains(".XLSX") || new_link.contains(".docx") || new_link.contains(".DOCX") ||
                            new_link.contains(".doc") || new_link.contains(".DOC") ||new_link.contains(".jpg") ||
                            new_link.contains(".jpeg") || new_link.contains(".png") || new_link.contains(".svg") ||
                            new_link.contains(".JPG") || new_link.contains(".JPEG") || new_link.contains(".PNG") || new_link.contains(".SVG") )
                    {
                        String fileUrl = new_link;

                        String fileExtension = "";

                        if(new_link.contains(".pdf")){
                            fileExtension = ".pdf";
                        }
                        else if(new_link.contains(".PDF")){
                            fileExtension = ".PDF";
                        }
                        else if(new_link.contains(".xlsx")){
                            fileExtension = ".xlsx";
                        }
                        else if(new_link.contains(".XLSX")){
                            fileExtension = ".XLSX";
                        }
                        else if(new_link.contains(".docx")){
                            fileExtension = ".docx";
                        }
                        else if(new_link.contains(".DOCX")){
                            fileExtension = ".DOCX";
                        }
                        else if(new_link.contains(".doc")){
                            fileExtension = ".doc";
                        }
                        else if(new_link.contains(".DOC")){
                            fileExtension = ".DOC";
                        }
                        else if(new_link.contains(".jpg")){
                            fileExtension = ".jpg";
                        }
                        else if(new_link.contains(".jpeg")){
                            fileExtension = ".jpeg";
                        }
                        else if(new_link.contains(".png")){
                            fileExtension = ".png";
                        }
                        else if(new_link.contains(".svg")){
                            fileExtension = ".svg";
                        }
                        else if(new_link.contains(".JPG")){
                            fileExtension = ".JPG";
                        }
                        else if(new_link.contains(".JPEG")){
                            fileExtension = ".JPEG";
                        }
                        else if(new_link.contains(".PNG")){
                            fileExtension = ".PNG";
                        }
                        else if(new_link.contains(".SVG")){
                            fileExtension = ".SVG";
                        }

                        try {
                            fileNo++;
                            downloadUsingNIO(fileUrl, "F:\\Java_Scraped_Files\\" + fileNo + fileExtension);

                        } catch (IOException e) {
                            fileNo--;
                        }

                        visited_urls.add(fileUrl);
                        continue;
                    }

                    local_urls_new.add(new_link);
                    visited_urls.add(new_link);
                }

                pageNo++;
                System.out.println("Scraped page " + pageNo);
                //System.out.println(url);
                //System.out.println(para);

                parse(depth+1, local_urls_new, final_urls, final_paras, faculty_urls, faculty_paras, visited_urls);

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException
    {
        ArrayList<String> local_urls = new ArrayList<String>();
        ArrayList<String> final_urls = new ArrayList<String>();
        ArrayList<String> final_paras = new ArrayList<String>();
        ArrayList<String> faculty_urls = new ArrayList<String>();
        ArrayList<String> faculty_paras = new ArrayList<String>();
        Set<String> visited_urls = new HashSet<String>();

        local_urls.add(base_url);
        visited_urls.add(base_url);
        int depth = 0;

        parse(depth, local_urls, final_urls, final_paras, faculty_urls, faculty_paras, visited_urls);

        // Writing to CSV
        File file = new File("General_crawler.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("LINK,PARAGRAPH");
        bw.newLine();
        for(int i=0;i<final_urls.size();i++)
        {
            String paragraph = final_paras.get(i).replace(",","");
            bw.write(final_urls.get(i)+","+paragraph);
            bw.newLine();
        }
        bw.close();
        fw.close();

        // Writing to CSV
        file = new File("Focused_crawler.csv");
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);

        bw.write("LINK,PARAGRAPH");
        bw.newLine();
        for(int i=0;i<faculty_urls.size();i++)
        {
            String paragraph = faculty_paras.get(i).replace(",","");
            bw.write(faculty_urls.get(i)+","+paragraph);
            bw.newLine();
        }
        bw.close();
        fw.close();

        System.out.println("Web crawler finished with crawling " + pageNo + " urls and downloading " + fileNo + " files.");
        System.out.println("Results are stored into two CSV files.");
    }
}