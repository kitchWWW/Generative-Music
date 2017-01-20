#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'8 d'8 e'4 dis'4 e'8 d'8 c'16 cis'16 d'16 a'16 b'4 a'8 b'8 c''4 d''4 c''4 b'8 a'8 g'4 f'8 e'8 d'4 f'8 g'16 a'16 b'4 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
