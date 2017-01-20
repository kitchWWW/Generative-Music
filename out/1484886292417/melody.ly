#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c''16 b'16 a'8 g'8 a'8 c''8 d''8 e''4 d''4 c''4 a'8 g'8 f'8 e'8 d'8 b8 a8. c'16 d'8. e'16 f'4 g'8. a'16 b'8 c''8 d''8 c''8 b'4 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
