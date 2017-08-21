package com.whjt.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import com.enterprise.support.utility.Validation;
import com.whjt.core.ProxyIpService.ProxyIPType;
import com.whjt.util.FileHelper;

public class HttpHelper {

    // private final static String ipUrl =
    // "http://ff.daili666.com/ip/?tid=849083587675097&num=1&ports=80&foreign=none";
    private final static String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0";
    private final static int MAX_TIMEOUT = 10000;

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     */
    public static String get(String url, String cookie, String charset,
            String proxyIPAddress, String proxyIPAddressPort, String refer)
            throws Exception {

        if (Validation.isEmpty(url)) {
            throw new Exception("参数异常、URL不能为空");
        }
        if (Validation.isEmpty(charset)) {
            charset = "UTF-8";
        }
        String result = null;

        HttpClient client = HttpClientUtil.getClient();

        HttpMethod method = new GetMethod(url);
        try {
            client.getParams().setContentCharset(charset);
            // client.getParams().setParameter(
            // ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            client.getParams().setSoTimeout(MAX_TIMEOUT);
            client.getParams().setConnectionManagerTimeout(MAX_TIMEOUT);
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(200, true));
            client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            client.getParams().setIntParameter("http.socket.timeout",
                    MAX_TIMEOUT);
            if (!Validation.isEmpty(proxyIPAddress)) {
                client.getHostConfiguration().setProxy(proxyIPAddress,
                        Integer.parseInt(proxyIPAddressPort));
            }
            method.getParams().setParameter("http.socket.timeout", MAX_TIMEOUT);
            method.setRequestHeader("Host", getHost(url));
            method.setRequestHeader("Accept-Language",
                    "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            method.setRequestHeader("User-Agent", agent);
            method.setRequestHeader("Connection", " Keep-Alive");
            method.setRequestHeader("Content-Type",
                    "application/x-javascript;charset=" + charset);
            method.setRequestHeader("Accept", "*/*");
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            if (!Validation.isEmpty(cookie)) {
                method.setRequestHeader("Cookie", cookie);
            }
            if (!Validation.isEmpty(refer)) {
                method.setRequestHeader("Referer", refer);
            }
            int stats = client.executeMethod(method);
            if (stats == HttpStatus.SC_OK) {
                Header header = method.getResponseHeader("Content-Encoding");
                if (header != null && !Validation.isEmpty(header.getValue())
                        && header.getValue().contains("gzip")) {
                    InputStream input = new GZIPInputStream(
                            method.getResponseBodyAsStream());
                    result = IOUtils.toString(input, charset);
                    IOUtils.closeQuietly(input);
                } else {
                    result = method.getResponseBodyAsString();
                }
            }
        } catch (Exception e) {
            method.abort();
            e.printStackTrace();
            return null;
        } finally {
            method.abort();
            method.releaseConnection();
        }
        return result;
    }

    public static Map<String, String> getResponseAndHeader(String url,
            String cookie, String charset, String proxyIPAddress,
            String proxyIPAddressPort, String refer) throws Exception {

        Map<String, String> resMap = new HashMap<String, String>();
        if (Validation.isEmpty(url)) {
            throw new Exception("参数异常、URL不能为空");
        }
        if (Validation.isEmpty(charset)) {
            charset = "UTF-8";
        }
        String result = null;
        String resCookies = null;
        HttpClient client = HttpClientUtil.getClient();
        ;
        HttpMethod method = new GetMethod(url);
        try {
            client.getParams().setContentCharset(charset);
            client.getParams().setSoTimeout(MAX_TIMEOUT);
            // client.getParams().setParameter(
            // ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            client.getParams().setConnectionManagerTimeout(MAX_TIMEOUT);
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(200, true));
            // client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            client.getParams().setCookiePolicy(
                    CookiePolicy.BROWSER_COMPATIBILITY);
            client.getParams().setIntParameter("http.socket.timeout",
                    MAX_TIMEOUT);
            if (!Validation.isEmpty(proxyIPAddress)) {
                client.getHostConfiguration().setProxy(proxyIPAddress,
                        Integer.parseInt(proxyIPAddressPort));
            }
            method.getParams().setParameter("http.socket.timeout", MAX_TIMEOUT);
            method.setRequestHeader("Host", getHost(url));
            method.setRequestHeader("User-Agent", agent);
            method.setRequestHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            method.setRequestHeader("Accept-Language",
                    "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            method.setRequestHeader("Accept-Encoding", "gzip, deflate");
            method.setRequestHeader("Connection", " Keep-Alive");
            method.setRequestHeader("Content-Type",
                    "application/x-javascript;charset=" + charset);
            method.setRequestHeader("Accept", "*/*");
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            if (!Validation.isEmpty(cookie)) {
                method.setRequestHeader("Cookie", cookie);
            }
            method.setRequestHeader("Connection", " Keep-Alive");
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            if (!Validation.isEmpty(refer)) {
                method.setRequestHeader("Referer", refer);
            }
            int stats;

            stats = client.executeMethod(method);

            if (stats == HttpStatus.SC_OK) {

                Header header = method.getResponseHeader("Content-Encoding");
                if (header != null && !Validation.isEmpty(header.getValue())
                        && header.getValue().contains("gzip")) {
                    InputStream input = new GZIPInputStream(
                            method.getResponseBodyAsStream());
                    result = IOUtils.toString(input, charset);
                    IOUtils.closeQuietly(input);
                } else {
                    result = method.getResponseBodyAsString();
                }
                resMap.put("result", result);
                Cookie[] cookies = client.getState().getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].toString().contains("cookie2")) {
                        resCookies = cookies[i].toString();
                        resMap.put("resCookies", resCookies);
                    }
                }
            } else if (204 == stats) {
                Header header = method.getResponseHeader("Set-Cookie");
                String cookies = header.getValue();
                resMap.put("resCookies", cookies);
            }
        } catch (Exception e) {
            method.abort();
            // e.printStackTrace ();
            return resMap;
        } finally {
            method.abort();
            method.releaseConnection();
        }
        return resMap;
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     */
    public static String getResponseCookies(String url, String cookie,
            String charset, String proxyIPAddress, String proxyIPAddressPort,
            String refer) throws Exception {
        if (Validation.isEmpty(url)) {
            throw new Exception("参数异常、URL不能为空");
        }
        if (Validation.isEmpty(charset)) {
            charset = "UTF-8";
        }
        String result = null;
        HttpClient client = HttpClientUtil.getClient();
        ;
        HttpMethod method = new GetMethod(url);
        try {
            client.getParams().setContentCharset(charset);
            client.getParams().setSoTimeout(3000);
            client.getParams().setConnectionManagerTimeout(5000);
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(200, true));
            client.getParams().setCookiePolicy(
                    CookiePolicy.BROWSER_COMPATIBILITY);
            client.getParams().setIntParameter("http.socket.timeout", 3000);
            if (!Validation.isEmpty(proxyIPAddress)) {
                client.getHostConfiguration().setProxy(proxyIPAddress,
                        Integer.parseInt(proxyIPAddressPort));
            }
            method.getParams().setParameter("http.socket.timeout", 3000);
            method.setRequestHeader("Host", getHost(url));
            method.setRequestHeader("Accept-Language",
                    "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            method.setRequestHeader("User-Agent", agent);
            method.setRequestHeader("Connection", " Keep-Alive");
            method.setRequestHeader("Content-Type",
                    "application/x-javascript;charset=" + charset);
            method.setRequestHeader("Accept", "*/*");
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            if (!Validation.isEmpty(cookie)) {
                method.setRequestHeader("Cookie", cookie);
            }
            if (!Validation.isEmpty(refer)) {
                method.setRequestHeader("Referer", refer);
            }
            int stats = client.executeMethod(method);

            if (stats == HttpStatus.SC_OK) {
                Cookie[] cookies = client.getState().getCookies();
                for (int i = 0; i < cookies.length; i++) {

                    if (cookies[i].toString().contains("cookie2")) {
                        result = cookies[i].toString();
                    }
                }
            }
        } catch (Exception e) {
            method.abort();
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }
        return result;
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static String get(String url, String charset, String refer)
            throws Exception {
        Thread.sleep(1000);
        List<String> list = ProxyIpService.getValidIpAddressAndPort();
        String ip = list.get(0).trim();
        String port = list.get(1).trim();
        try {
            return get(url, null, charset, ip, port, refer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static String getTest(String url, String refer) throws Exception {
        Thread.sleep(1000);

        return get(
                url,
                "t=44e936195c92e7335bc216d43be88adc; _tb_token_=iriDnGd0UkgO; cookie2=ba7babf18cd41f2afef11f2db92d9ba1; pnm_cku822=214fCJmZk4PGRVHHxtEb3sma3Q%2FaC51PWsQKg%3D%3D%7CfyJ6ZyByNmcvbnsranohZBU%3D%7CfiB4D157YHtufDUqfHY4fmU0eysVDBZUXF8KRG0c%7CeSRiYjNhJ385dWs5fm82dG8tdDZxN3FjNXdmPHlmI3E7ay5ucTQd%7CeCVoaEASTBRVGQ1GBw1DEBVdQ0wHKgM%3D%7CeyR8C0kZWRxcBBNGBBFRFgxHEkwUVhoMRwQQTBQJSBNRFFIeCDIb%7CeiJmeiV2KHMvangudmM6eXk%2BAA%3D%3D; cna=nvXwDDWRNzACAT2r3RqSXjc4; isg=C55FCFF1E81257F8417B70B491AFFCD0; cq=ccp%3D1; CNZZDATA1000279581=618365555-1416806415-%7C1416882015",
                "GBK", null, null, refer);
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static Map<String, String> getResult(String url, String cookie,
            String charset, String refer, ProxyIPType type, String againIP)
            throws Exception {
        Thread.sleep(1000);
        Map<String, String> resultsMap = new HashMap<String, String>();
        String[] ips = null;
        String ipContent = null;
        try {
            if (null == againIP) {
                if (url.indexOf("show_buyer_list") > 0
                        || url.indexOf("ext.mdskip") > 0) {
                    ipContent = ProxyIpService.getProxyIpInfo_TR();
                    ips = ipContent.split(":");
                } else {
                    ipContent = ProxyIpService.getProxyIpInfoTR();
                    ips = ipContent.split(":");
                }
            } else {
                ips = againIP.split(":");
                ipContent = againIP;
            }

            resultsMap = getResponseAndHeader(url, cookie, charset,
                    ips[0].trim(), ips[1].trim(), refer);
            resultsMap.put("ip", ipContent);
            return resultsMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return resultsMap;
        }
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static String getTestJd(String url, String refer) throws Exception {
        Thread.sleep(1000);
        return get(url, null, "GBK", null, null, refer);
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        Thread.sleep(2000);
        return get(url, null, null, null, null, null);
    }

    /**
     * 发起Get请求
     * 
     * @param url
     * @param charset
     * @param cookies
     * @return
     * @throws Exception
     */
    public static String get(String url, String charset, String cookies,
            String refer) throws Exception {
        List<String> list = ProxyIpService.getValidIpAddressAndPort();
        String ip = list.get(0).trim();
        String port = list.get(1).trim();
        return get(url, charset, cookies, ip, port, refer);
    }

    /**
     * 获取URL HOST 地址
     * 
     * @param url
     * @return
     */
    public static String getHost(String url) {
        url = url.substring(7);
        // 对淘宝用户的特殊处理
        if (-1 < url.indexOf("i.whjt.com")) {

            url = url.replaceAll("i.taobao.com", "my.whjt.com");
        }
        return url.substring(0,
                url.indexOf("/") == -1 ? url.length() : url.indexOf("/"));
    }

    /**
     * 下载 url 保存到 savePath
     * 
     * @param url
     * @param savePath
     */
    public static boolean writePicDownloaderFile(String urlPath,
            String savePath, String fileName) {
        InputStream input = null;
        FileOutputStream output = null;
        File path = new File(savePath);
        File finallyPath = new File(savePath + "//" + fileName);
        if (!path.getAbsoluteFile().exists()) {
            path.getAbsoluteFile().mkdirs();
        } else {
            FileHelper.delFile(savePath);
        }
        try {
            URL url = new URL(urlPath);
            input = url.openStream();
            output = new FileOutputStream(finallyPath.getAbsolutePath());
            IOUtils.copy(input, output);
            output.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * @Title: getResult
     * @Description: POST请求
     * @author Grass
     * @param url
     * @param cookie
     * @param charset
     * @param refer
     * @param type
     * @param againIP
     * @return
     * @throws Exception
     * @throws
     */
    public static Map<String, String> getResultWithPost(String url,
            String cookie, String charset, String refer, String origin,
            Map<String, String> postPara, ProxyIPType type, String againIP)
            throws Exception {

        Thread.sleep(1000);
        Map<String, String> resultsMap = new HashMap<String, String>();
        String[] ips = null;
        String ipContent = null;

        try {
            if (null == againIP) {
                if (url.indexOf("show_buyer_list") > -1
                        || url.indexOf("ext.mdskip") > -1) {

                    ipContent = ProxyIpService.getProxyIpInfo_TR();
                    ips = ipContent.split(":");
                } else {

                    ipContent = ProxyIpService.getProxyIpInfoTR();
                    ips = ipContent.split(":");
                }
            } else {
                ips = againIP.split(":");
                ipContent = againIP;
            }

            resultsMap = getResponseAndHeaderWithPost(url, cookie, charset,
                    ips[0].trim(), ips[1].trim(), refer, origin, postPara);
            resultsMap.put("ip", ipContent);

            return resultsMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return resultsMap;
        }
    }

    public static Map<String, String> getResponseAndHeaderWithPost(String url,
            String cookie, String charset, String proxyIPAddress,
            String proxyIPAddressPort, String refer, String origin,
            Map<String, String> postPara) throws Exception {

        Map<String, String> resMap = new HashMap<String, String>();

        if (Validation.isEmpty(url)) {

            throw new Exception("参数异常、URL不能为空");
        }

        if (Validation.isEmpty(charset)) {

            charset = "UTF-8";
        }
        String result = null;
        String resCookies = null;
        HttpClient client = HttpClientUtil.getClient();

        PostMethod method = new PostMethod(url);
        try {

            // 连接超时
            client.getParams().setConnectionManagerTimeout(MAX_TIMEOUT);
            // 端口超时
            client.getParams().setSoTimeout(MAX_TIMEOUT);
            client.getParams().setIntParameter("http.socket.timeout",
                    MAX_TIMEOUT);
            // 字符
            client.getParams().setContentCharset(charset);
            client.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            // Cookie策略
            // client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            client.getParams().setCookiePolicy(
                    CookiePolicy.BROWSER_COMPATIBILITY);
            // 失败重试
            client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(200, true));

            // 代理IP
            if (!Validation.isEmpty(proxyIPAddress)) {

                client.getHostConfiguration().setProxy(proxyIPAddress,
                        Integer.parseInt(proxyIPAddressPort));
            }

            method.getParams().setParameter("http.socket.timeout", MAX_TIMEOUT);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler());
            // 请求头信息
            method.setRequestHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            method.setRequestHeader("Accept-Encoding", "gzip, deflate");
            method.setRequestHeader("Accept-Language",
                    "zh-CN,zh;q=0.8,en;q=0.6");
            method.setRequestHeader("Cache-Control", "max-age=0");
            method.setRequestHeader("Connection", "keep-alive");
            method.setRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            if (!Validation.isEmpty(cookie)) {
                method.setRequestHeader("Cookie", cookie);
            }
            method.setRequestHeader("Host", getHost(url));
            if (!Validation.isEmpty(origin)) {
                method.setRequestHeader("Origin", origin);
            }
            if (!Validation.isEmpty(refer)) {
                method.setRequestHeader("Referer", refer);
            }
            method.setRequestHeader("User-Agent", agent);

            // 设置Post请求参数
            Set<String> keySet = postPara.keySet();
            int setSize = keySet.size();
            NameValuePair[] nvArr = new NameValuePair[setSize];
            int index = 0;
            for (String temKey : keySet) {

                String temVal = postPara.get(temKey);
                nvArr[index] = new NameValuePair(temKey, temVal);
                index++;
            }
            method.setRequestBody(nvArr);

            int stats = client.executeMethod(method);

            if (stats == HttpStatus.SC_OK) {

                Header header = method.getResponseHeader("Content-Encoding");
                if (header != null && !Validation.isEmpty(header.getValue())
                        && header.getValue().contains("gzip")) {
                    InputStream input = new GZIPInputStream(
                            method.getResponseBodyAsStream());
                    result = IOUtils.toString(input, charset);
                    IOUtils.closeQuietly(input);
                } else {
                    result = method.getResponseBodyAsString();
                }
                resMap.put("result", result);
                Cookie[] cookies = client.getState().getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].toString().contains("cookie2")) {
                        resCookies = cookies[i].toString();
                        resMap.put("resCookies", resCookies);
                    }
                }
            } else if (204 == stats) {
                Header header = method.getResponseHeader("Set-Cookie");
                String cookies = header.getValue();
                resMap.put("resCookies", cookies);
            }
        } catch (Exception e) {
            method.abort();
            // e.printStackTrace();
            return resMap;
        } finally {
            method.abort();
            method.releaseConnection();
        }
        return resMap;
    }
}
