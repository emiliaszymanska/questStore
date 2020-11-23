export function getCookie(cookieName) {

    let name = cookieName + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let splitCookie = decodedCookie.split(';');
    for (let i = 0; i < splitCookie.length; i++) {
        let cookie = splitCookie[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}