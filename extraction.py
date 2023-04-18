import re

presude = ["""OSNOVNI SUD U BARU, sudija Tamara Spasojević, sa zapisničarom Anom Stanišić u krivičnom predmetu
okrivljenog B. A., zbog krivičnog djela krađa čl.239 st.1 Krivičnog zakonika Crne Gore, po optužnom predlogu
Osnovnog državnog tužilaštva u Baru Kt.br.354/21 od 20.07.2021.godine, nakon održanog glavnog, javnog
pretresa u prisustvu zastupnika optužbe savjetnika kod Osnovnog državnog tužilaštva U Baru P. Đ. i
okrivljenog, dana 05.08.2021.godine donio je i istog dana javno objavio""", """OSNOVNI SUD U PODGORICI, sudija Larisa Mijušković-Stamatović, uz učešće zapisničara Marine Sekulić, u
krivičnom postupku protiv okrivljenog D.A., koga brani branilac po službenoj dužnosti V. V., advokat iz P.,
zbog krivičnog djela krađa iz čl. 239 st. 1 Krivičnog zakonika Crne Gore""", """OSNOVNI SUD U DANILOVGRADU, kao krivični, sudija Sonja Keković, uz učešće Ane Ivanović, kao zapisničara,
u krivičnom predmetu protiv okrivljenog T. D.a iz N., zbog krivičnog djela krađa iz čl.239 st.1 Krivičnog
zakonika Crne Gore, odlučujući po optužnom predlogu Osnovnog državnog tužilaštva u N.u Kt.br.522/20 od
25.11.2020. godine""", """OSNOVNI SUD U BARU, sudija Tamara Spasojević, sa zapisničarom Anom Stanišić u krivičnom predmetu
okrivljenog B. A.. , zbog krivičnog djela krađa iz čl.239 st.1 Krivičnog zakonika Crne Gore, po optužnom
predlogu Osnovnog državnog tužilaštva u Baru Kt.br.339/21 od 19.08.2021.godine, nakon održanog glavnog,
javnog pretresa u prisustvu zastupnika optužbe savjetnika kod Osnovnog državnog tužilaštva u Baru Darinke
Šćepanović i okrivljenog, dana 29.10.2021.godine donio je i istog dana javno objavio""", """OSNOVNI SUD U BARU, sudija Tamara Spasojević, sa zapisničarom Anom Stanišić u krivičnom predmetu
okrivljene S. M., zbog produženog krivičnog djela krađa čl.239 st.1 u vezi čl. 49 Krivičnog zakonika Crne Gore,
po optužnom predlogu Osnovnog državnog tužilaštva u Baru Kt.br.407/21 od 24.12.2021.godine, nakon
održanog glavnog, javnog pretresa u prisustvu zastupnika optužbe Državnog tužioca kod Osnovnog državnog
tužilaštva u Baru V. G. i okrivljene, dana 16.03.2022.godine donio je i istog dana javno objavio""", """OSNOVNI SUD U BARU, sudija Tamara Spasojević, sa zapisničarom Anom Stanišić, u krivičnom predmetu
okrivljenog S. M. , zbog krivičnog djela - teška kradja iz čl.240 st.1 tač.1 Krivičnog zakonika Crne Gore, po
optužnici Osnovnog državnog tužilaštva u Baru KT.br.1/21 od 03.03.2021.godine, nakon održanog glavnog i
javnog pretresa dana 13.04.2021.godine u prisustvu zastupnika optužbe Osnovnog državnog tužioca u Baru
Bisere Hajdarpašić, optuženog i branioca po službenoj dužnosti optuženog L. D. , advokata iz B. , dana
15.04.2021.godine donio i javno objavio""", """OSNOVNI SUD U BARU, kao prvostepeni krivični, sudija Goran Šćepanović, kao sudija pojedinac, sa
zapisničarkom Milijanom Minić, u krivičnom predmetu okrivljenog V. R., zbog krivičnog djela krađa iz čl. 239
st.1 Krivičnog zakonika Crne Gore, po optužnom predlogu Osnovnog državnog tužilaštva u Baru Kt.br. 419/21
od 27.09.2021. godine, nakon održanog glavnog javnog pretresa u prisustvu savjetnice u Osnovnog državnom
tužilaštvu u Baru Š. D., okrivljenog i njegovog branioca adv B. S., dana 02.12.2021. godine donio je i javno
objavio,
"""]

def _n_words_after_word(words: list, word: str, n: int):
    filtered = filter(lambda x: word in x, words)
    index = next(map(words.index, filtered), None)
    return words[slice(index + 1, index + 1 + n)]

def extract(text):
    terms = re.split(r',\s*', text)

    sud = terms[0]

    for term in terms:
        if 'sudija' in term:
            sudija = ' '.join(term.split(' ')[1:3])
        if 'zapisnič' in term:
            zapisnicar = ' '.join(_n_words_after_word(term.split(' '), 'zapisnič', 2))
        if 'okriv' in term:
            okrivljeni = ''.join(_n_words_after_word(term.split(' '), 'okriv', 2))
            return sud, sudija, zapisnicar, okrivljeni
        if 'optuž' in term:
            optuzeni = ''.join(_n_words_after_word(term.split(' '), 'optuž', 2))
            return sud, sudija, zapisnicar, optuzeni
        
def main():
    with open('features.csv', 'w', encoding="utf-8") as f:
        f.write('sudija, zapisnicar, okrivljeni, \n')
        for presuda in presude:
            f.write(','.join(extract(presuda)))
            f.write('\n')


if __name__ == "__main__":
    main()
    

