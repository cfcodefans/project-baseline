function cleanInput(ev) {
    var t = $(ev.target).siblings()[0]
    if (!(t && "INPUT" == t.tagName.toUpperCase()
            && (t.type=="text"
                || t.type=="password"
                || t.type=="mail"
                || t.type=="tel"
                || t.type=="number"
                || t.type=="file"))) return
    t.value = ""
}