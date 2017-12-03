# NUR_guitar_songbook


Aby jsme si nerozbili git vytvorte si kazdy svou branch a pak mergujte do mastru, pushe do mastru jsem zakazal

## gotchas

### nastaveni barvy drawable

val circle = activity.resources.getDrawable(R.drawable.circle_drawable) as GradientDrawable
circle.setColor(Color.argb(255, 255, 0, 0))
view.avatar.background = circle

### SelectableListFragment
musí obsahovat checkbox s ID checkbox
selekční mod se kontroluje přes selector

viz https://github.com/sephiroth74/recyclerview-multiselect

### file chooser dialog

https://github.com/angads25/android-filepicker/wiki/filepicker-dialog
