function cleanText(ev) {
    var t = $(ev.target).siblings()[0]
    if (!(t && "INPUT" == t.tagName.toUpperCase() && t.type=="text" || t.type=="password")) return
    t.value = ""
}