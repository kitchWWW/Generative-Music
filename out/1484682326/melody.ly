#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'16 b8. g8 a8 c'8 d'8 e'8 d'8 c'4 b8 c'8 e'16 f'8 e'16 f'4 f'8. e'16 d'4 e'16 d'16 e'8 f'8 a'8 b'4 c''4 d''8 c''8 b'4 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
