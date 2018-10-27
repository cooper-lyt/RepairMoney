

(function ($) {
    jQuery.fn.describeDisplay = function () {
        return this.each(function () {
            var $this = $(this)
            var data  = $this.data('describeDisplay')

            if (!data) {
                var lines = $this.data('json');
                $.each(lines, function (i, n){
                    var summary_html = '<p class="summary col-md-' + n.widthBlock +'">';
                    if(n.label){
                        summary_html += '<label>' + n.label + '</label>'
                    }
                    summary_html += n.context + '</p>';
                    $this.append(summary_html);

                });

                $this.data('describeDisplay', {target: $this})
            }

        })
    };

    $(window).on('load.describeDisplay', function () {


        $('[data-describe="json"]').each(function () {
            $(this).describeDisplay();
        })
    })

})(jQuery);
