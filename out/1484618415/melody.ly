#(set-global-staff-size 25)
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#t #t #t) c'16 d'16 e'8 g'8 a'8 b'4 c''4 b'16 c''16 b'8 a'8 g'8 f'8 d'8 c'4 d'4 f'8 a'8 b'8 a'8 g'16 a'8. b'8. a'16 g'8 f'8 e'4 d'4 c'4 \bar"|."
}

>>
\midi{}
\layout{}
}
